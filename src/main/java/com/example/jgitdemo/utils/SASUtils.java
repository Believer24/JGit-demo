package com.example.jgitdemo.utils;

import com.sas.iom.SAS.ILanguageService;
import com.sas.iom.SAS.ILanguageServicePackage.CarriageControlSeqHolder;
import com.sas.iom.SAS.ILanguageServicePackage.LineTypeSeqHolder;
import com.sas.iom.SAS.IWorkspace;
import com.sas.iom.SAS.IWorkspaceHelper;
import com.sas.iom.SASIOMDefs.GenericError;
import com.sas.iom.SASIOMDefs.StringSeqHolder;
import com.sas.services.connection.*;

import java.io.*;

public class SASUtils {
    public void runCreateSAS(String path, Integer id, String project) throws IOException, GenericError, ConnectionFactoryException {
        ZeroConfigWorkspaceServer server = new ZeroConfigWorkspaceServer();
        ManualConnectionFactoryConfiguration config = new ManualConnectionFactoryConfiguration(server);
        ConnectionFactoryManager manager = new ConnectionFactoryManager();
        ConnectionFactoryInterface factory = manager.getFactory(config);
        SecurityPackageCredential cred = new SecurityPackageCredential();
        ConnectionInterface cx = factory.getConnection(cred);
        try {
            // Narrow the connection from the server.
            org.omg.CORBA.Object obj = cx.getObject();
            IWorkspace iWorkspace = IWorkspaceHelper.narrow(obj);

            //insert iWorkspace workspace usage code here
            ILanguageService iLanguageService = iWorkspace.LanguageService();
            iLanguageService.Submit(readSAS(path, id, project));

            CarriageControlSeqHolder logCarriageControlHldr = new CarriageControlSeqHolder();
            LineTypeSeqHolder logLineTypeHldr = new LineTypeSeqHolder();
            StringSeqHolder logHldr = new StringSeqHolder();
            iLanguageService.FlushLogLines(Integer.MAX_VALUE,logCarriageControlHldr, logLineTypeHldr, logHldr);
            String[] logLines = logHldr.value;
            for ( int i=0; i < logLines.length; i++){
                System.out.println(logLines[i]);
            }
        } finally{
            cx.close();
        }
    }

    public String readSAS(String path, Integer id, String project) throws IOException {

        String envir = null;
        switch (id){
            case 1:
                envir = "Production";
                break;
            case 2:
                envir = "Validation";
                break;
            case 3:
                envir = "Sandbox";
                break;
        }

        String replaceStr1 = "&envir";
        String replaceStr2 = "&project.";

        File file = new File(path);
        InputStreamReader in = new InputStreamReader ( new FileInputStream(file),"GBK");
        BufferedReader bufIn = new BufferedReader(in);

        // 替换
        String line = "";
        StringBuilder stringBuffer = new StringBuilder();
        while ((line = bufIn.readLine()) != null) {
            line = line.replaceAll(replaceStr1, envir).replaceAll(replaceStr2,project);
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }
}

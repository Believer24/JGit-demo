package com.example.jgitdemo;

import com.example.jgitdemo.service.RepositoryProvider;
import com.example.jgitdemo.service.impl.RepositoryProviderCloneImpl;
import com.example.jgitdemo.service.impl.RepositoryProviderExistingClientImpl;
import com.example.jgitdemo.utils.CSVToJSON;
import com.example.jgitdemo.utils.GitlabUtil;
import com.sas.iom.SAS.ILanguageService;
import com.sas.iom.SAS.IWorkspace;
import com.sas.iom.SAS.IWorkspaceHelper;
import com.sas.services.connection.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class JGitDemoApplicationTests {


//    private  RepositoryProvider repoProvider = new RepositoryProviderCloneImpl("C:\\Work\\JGit-demo\\.git", "C:\\Work\\demo");
private static RepositoryProvider repoProvider = new RepositoryProviderExistingClientImpl("C:\\Work\\JGit-demo\\.git");

private static  UsernamePasswordCredentialsProvider provider =
        new UsernamePasswordCredentialsProvider("Believer24", "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");

    private static  UsernamePasswordCredentialsProvider provider22 =
            new UsernamePasswordCredentialsProvider("Believer24", "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");

    @Test
    void contextLoads() throws GitAPIException {
        //推到远程仓库
        UsernamePasswordCredentialsProvider provider =
                new UsernamePasswordCredentialsProvider("Believer24", "Zh19930315");
        Git git = Git.cloneRepository()
                .setBranch("main")
                .setURI("https://github.com/Believer24/JGit-demo")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("Believer24", "Zh19930315"))//设置git远端URL
                .setDirectory(new File("C:\\Work\\JGit"))  //设置本地仓库位置
                .call(); //启动命令
        //add 命令
//        try {
//            Git.open(new File("C:\\Work\\JGit-demo\\.git"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        git.add().addFilepattern(".").call();
//        CommitCommand commit = git.commit();

        // 初始化仓库
        Repository repository;
        try {
            repository = FileRepositoryBuilder.create(new File("C:\\Work\\JGit"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void testPush() {
        try {
            GitlabUtil.pushCommand("Believer24", "Zh19930315", new File("C:\\Work\\JGit"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCSVToJSON() throws IOException {
        CSVToJSON csvToJSON = new CSVToJSON();
        csvToJSON.testCSVToJSON();
    }

    @Test
    void testSASConn() throws ConnectionFactoryException {
        ZeroConfigWorkspaceServer server = new ZeroConfigWorkspaceServer();
        ManualConnectionFactoryConfiguration config = new ManualConnectionFactoryConfiguration(server);
        ConnectionFactoryManager manager = new ConnectionFactoryManager();
        ConnectionFactoryInterface factory = manager.getFactory(config);
        SecurityPackageCredential cred = new SecurityPackageCredential();
        ConnectionInterface cx = factory.getConnection(cred);
        // Narrow the connection from the server.
        org.omg.CORBA.Object obj = cx.getObject();
        IWorkspace iWorkspace = IWorkspaceHelper.narrow(obj);

        //insert iWorkspace workspace usage code here
        ILanguageService iLanguageService = iWorkspace.LanguageService();
    }


    @Test
    void testCreateLocalRepo() throws IOException {
        GitlabUtil.creatLocalRepo("C:\\Work\\JGit-demo\\.git");
    }

    @Test
    void testJGit() throws Exception {
//        RepositoryProvider tstProvider = new RepositoryProviderCloneImpl("https://github.com/Believer24/JGit-demo.git", "C:\\Work\\JGit");
        RepositoryProvider trepoProvider = new RepositoryProviderExistingClientImpl("C:\\Work\\JGit\\.git");
        try (Git git = new Git(trepoProvider.get())) {
            git.checkout().setName("origin/master").call();
            git.pull().call();
        }
    }


    //代码提交
    @Test
    void testAddCommitPush() throws Exception {
        RepositoryProvider repProvider = new RepositoryProviderExistingClientImpl("C:\\Work\\JGit-demo\\.git");
        try (Repository repo = repProvider.get();
             Git git = new Git(repo)) {
//            StatusCommand status = git.status();
            git.checkout().setName("master").call();
            git.add().addFilepattern(".").call();
            // 创建dev分支
//            git.branchCreate().setName("dev").call();
//            git.checkout().setName("dev").call();
            git.commit().setMessage("this is local dev push to remote dev").call();
            git.push().setCredentialsProvider( new UsernamePasswordCredentialsProvider("Believer24", "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")).call();
        }
    }
}

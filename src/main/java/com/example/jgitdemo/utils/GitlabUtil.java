package com.example.jgitdemo.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class GitlabUtil {
    public static String REP_ROOT_DIR = "C:\\Work\\JGit-demo";

    /**
     * 根据配置获得最新的git代码
     * @param repUrl
     * @param user
     * @param password
     * @throws Exception
     */
    public synchronized static void getLatestScripts(String repUrl, String user, String password) throws Exception {
        File file = new File(REP_ROOT_DIR);
        if(file.exists()) {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository rep = builder.setGitDir(new File(REP_ROOT_DIR + "/.git")).readEnvironment()
                    .findGitDir()
                    .build();
            Git git = new Git(rep);
//            git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password))
//                    .setRemote("origin").setRemoteBranchName("master").call();
        } else {
            Git.cloneRepository().setDirectory(file).setURI(repUrl)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password)).call().close();
        }
    }

    /**
     * 上传指定文件更新到服务器
     * @param user
     * @param password
     * @throws Exception
     */
    public synchronized static void pushCommand(String user, String password, File file) throws Exception {
        CredentialsProvider credentialsProvider
                = new UsernamePasswordCredentialsProvider(user,"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" );
        if(file != null) {
            Git git = Git.open(file);
            git.cloneRepository().setBranch("master")
                    .setURI("https://github.com/Believer24/JGit-demo")
                    .setDirectory(file)
                    .call();
            git.add().addFilepattern(file.getPath()).call();
            git.commit().setMessage( "上传备注" ).call();
            Status status = git.status().call();
            int add = status.getAdded().size();
            System.out.println("新增个文件:"+ add);
            git.push().setRemote("origin").add("master").setCredentialsProvider(credentialsProvider).call();

        }
    }


    public static void commitFiles() throws IOException, GitAPIException {
        String filePath = "";
        Git git = Git.open( new File("C:\\Work\\JGit-demo\\.git") );
        //创建用户文件的过程
        File myfile = new File(filePath);
        myfile.createNewFile();
        git.add().addFilepattern("*").call();
        //提交
        git.commit().setMessage("test commit").call();
        //推送到远程
        git.push().call();
    }

    public static void creatLocalRepo(String path) throws IOException {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(path))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .setMustExist(true)
                .build();
    }

    public static void createFileFromGitRoot(Repository repo, String filename, String content) throws FileNotFoundException {
        File hello3 = new File(repo.getDirectory().getParent(), filename);
        try (PrintWriter out = new PrintWriter(hello3)) {
            System.out.println(content);
        }
    }
}

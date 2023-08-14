package com.example.jgitdemo.utils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class GitlabUtil {
    public static String REP_ROOT_DIR = "C:\\Work\\JGit-demo\\.git";

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
                = new UsernamePasswordCredentialsProvider( "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", "" );
        if(file != null) {
            Git git = Git.open(new File(REP_ROOT_DIR ));
            git.add().addFilepattern(".").call();
            git.commit().setMessage( "后台自动上传备注" ).call();
            Iterable<PushResult> list = git.push().setRemote("git@github.com:Believer24/JGit-demo.git").add("main").setCredentialsProvider(credentialsProvider).call();
            if(list != null) {
                for(PushResult pr : list) {
                    System.out.println(pr.getMessages());
                }
            }
        }
    }
}

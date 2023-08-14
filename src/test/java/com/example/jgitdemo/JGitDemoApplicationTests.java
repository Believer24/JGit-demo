package com.example.jgitdemo;

import com.example.jgitdemo.utils.GitlabUtil;
import org.apache.commons.logging.Log;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class JGitDemoApplicationTests {

    @Test
    void contextLoads() throws GitAPIException {
        //推到远程仓库
        UsernamePasswordCredentialsProvider provider =
                new UsernamePasswordCredentialsProvider("Believer24", "Zh19930315");
        Git git = Git.cloneRepository()
                .setBranch("dev")
                .setURI("https://github.com/Believer24/JGit-demo")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("Believer24", "Zh19930315"))//设置git远端URL
                .setDirectory(new File("C:\\Work\\JGit"))  //设置本地仓库位置
                .call(); //启动命令
        //add 命令
        try {
            Git.open(new File("C:\\Work\\JGit-demo\\.git"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        git.add().addFilepattern(".").call();
//        CommitCommand commit = git.commit();

        // 初始化仓库
        Repository repository;
        try {
            repository = FileRepositoryBuilder.create(new File("C:\\Work\\JGit-demo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void testPush(){
        try {
            GitlabUtil.pushCommand("Believer24", "Zh19930315", new File("C:\\Work\\JGit-demo"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

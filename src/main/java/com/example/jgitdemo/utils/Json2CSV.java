package com.example.jgitdemo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @program: ClickhouseSink
 * @ClassName Json2CSV
 * @description:
 * @author:
 * @create: 2022-04-13 09:06
 * @Version 1.0
 **/
public class Json2CSV {
    public  void Json2CSV() {
        File file = new File("D:\\Code\\IDEA\\ClickhouseSink\\input\\aaa.json");//待读取文件
        String content;
        if(file.exists()){
            System.out.println("文件存在。不用创建");
            try{
                String str;
                StringBuffer stringBuffer=new StringBuffer();
                // 读取文件的内容
                FileInputStream fileInputStream=new FileInputStream(file);
                InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                while ((content=bufferedReader.readLine())!=null){

                    //System.out.println(content);

                    JSONObject jsonObject = JSON.parseObject(content);

                    String id = jsonObject.getString("_id");

                    JSONObject source = jsonObject.getJSONObject("_source");
                    Integer broadcast = source.getInteger("broadcast");
                    String date = source.getString("date");
                    Integer drop = source.getInteger("drop");
                    Long timestamp = source.getLong("timestamp");

                    Date date1 = new Date();
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String upload_time = sdf.format(date1);

                    stringBuffer.append(id).append(",")
                            .append(broadcast).append(",")
                            .append(date).append(",")
                            .append(drop).append(",")
                            .append(timestamp).append(",")
                            .append(upload_time)
                            .append("\n");
                }
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
                //向文件写入内容
                File newfile=new File("D:\\Code\\IDEA\\ClickhouseSink\\input\\bbb.csv");//待写入文件
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile),"UTF-8"));//顺便练习下匿名类的使用

                bufferedWriter.write(stringBuffer.toString());
                bufferedWriter.close();
                System.out.println("文件写入内容完成");
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}


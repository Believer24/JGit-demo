package com.example.jgitdemo.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CSVToJSON {

    public void  testCSVToJSON() throws IOException {

        CsvMapper mapper = new CsvMapper();

        // CSV文件路径
        File csvFile = new File("C:\\home\\medcoding_report\\SHR3824-302_M-20210219-0001_不良事件_20230706_1726_CN_EN.csv");

        // 定义CSV的schema
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        // 读取CSV文件并转换为JSON
        MappingIterator<Map<?, ?>> it = mapper.readerFor(Map.class).with(schema).readValues(csvFile);

        // 遍历JSON对象并处理
        while (it.hasNext()) {
            Map<?, ?> row = it.next();
            // process json object
            System.out.println(row);
        }
    }
}
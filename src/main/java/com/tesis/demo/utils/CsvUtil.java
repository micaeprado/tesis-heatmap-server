package com.tesis.demo.utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.util.List;

public class CsvUtil {

    public static List<String[]> readCsv(Reader reader, String separator, String quote) throws Exception {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator.charAt(0))
                .withIgnoreQuotations(false)
                .withQuoteChar(quote != null ? quote.charAt(0) : '"')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();

        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

}

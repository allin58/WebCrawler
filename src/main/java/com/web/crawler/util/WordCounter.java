package com.web.crawler.util;

public class WordCounter {

    public static Integer count(String text, String term) {
       if (!text.contains(term))
         return 0;

       int count =0;

       boolean existFlag = true;
       while(existFlag) {
           count++;
           text = text.substring(text.indexOf(term)+1);
           if (!text.contains(term)) {
               existFlag = false;
           }
       }


       return count;
    }

}

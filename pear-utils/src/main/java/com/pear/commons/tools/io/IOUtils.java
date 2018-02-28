package com.pear.commons.tools.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by littlehui on 2017/6/15.
 */
public class IOUtils {

    public static String in2Str(InputStream in, String encoding) {
        if (in == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader reader = new InputStreamReader(in, encoding);
            int tmp = -1;
            char temp;
            while ((tmp = reader.read()) != -1) {
                temp = (char)tmp;
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}

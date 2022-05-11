package io.github.zhiweicoding.catsmart;

import java.io.InputStream;
import java.net.URL;

/**
 * @Created by zhiwei on 2022/5/12.
 */
public class SelfXMlLoader {

    private SelfXMlLoader() {
    }

    public static URL loadURL(String path) {
        return SelfXMlLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return SelfXMlLoader.class.getResourceAsStream(name);
    }
}

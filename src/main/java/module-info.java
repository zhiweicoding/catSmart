/**
 * @Created by zhiwei on 2022/5/12.
 */
module catSmart {
    requires MaterialFX;
    requires VirtualizedFX;

    requires jdk.localedata;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires fr.brouillard.oss.cssfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.scenicview.scenicview;

    opens io.github.zhiweicoding.catsmart to javafx.fxml;
    exports io.github.zhiweicoding.catsmart;
    opens io.github.zhiweicoding.catsmart.controllers;
}
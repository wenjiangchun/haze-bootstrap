package com.xinyuan.haze.qrcode.common;

import java.awt.*;

/**
 * 二维码logo配置.
 * <p/>
 * Created by Sofar on 2015/1/28.
 */
public class LogoConfig {

    /**
     * 默认边框颜色
     */
    public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;

    /**
     * logo默认边框宽度(像素)
     */
    public static final int DEFAULT_BORDER_WIDTH = 5;

    /**
     * logo大小默认为照片的1/5
     */
    public static final int DEFAULT_LOGO_PART = 4;

    private final int border = DEFAULT_BORDER_WIDTH;

    private final Color borderColor;

    private final int logoPart;

    /**
     * Creates a default config with on color {@link java.awt.Color#BLACK} and off color
     * {@link java.awt.Color#WHITE}, generating normal black-on-white barcodes.
     */
    public LogoConfig() {
        this(DEFAULT_BORDER_COLOR, DEFAULT_LOGO_PART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}

package com.xinyuan.haze.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * 操作系统工具类
 *
 * @author sofar
 */
public final class OSUtils {

    /**
     * 操作系统文件分隔符
     */
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * 封装了操作系统信息的{@code map}对象
     * <p>
     *     <b>说明：</b>该类继承自{@link java.util.HashMap}, 在类加载过程中获取系统属性信息，一旦系统属性加载完毕则关闭
     *     向{@code Map} put内容功能，使该对象{@code static{}}代码块一旦执行完毕后，{@code OS_PROPERTIES_MAP}则不可更改其内容。
     * </p>
     * @see com.xinyuan.haze.common.utils.OSUtils.UnChangeHashMap
     */
    public static final Map<String, Object> OS_PROPERTIES_MAP = new UnChangeHashMap<>();;

    private static final Logger logger = LoggerFactory.getLogger(OSUtils.class);

    static {

        //获取操作系统信息并将操作系统信息放至{@code OS_PROPERTIES_MAP}中
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> {
            logger.debug("获取操作系统信息【key={},value={}】", k, v);
            OS_PROPERTIES_MAP.put((String) k, v);
        });
        //加载完毕后使其内容不能被改变
        ((UnChangeHashMap)OS_PROPERTIES_MAP).closeChange();
    }

    public OSUtils() {
        throw new UnsupportedOperationException("OSUtils不能被实例化！");
    }

    /**
     * 根据命令参数执行底层操作系统命令
     *
     * @param commond 命令参数
     * @throws java.io.IOException 执行失败抛出该异常
     */
    public static void execCommond(String commond) throws IOException {
        Assert.notNull(commond);
        logger.debug("准备执行系统命令【{}】", commond);
        Runtime.getRuntime().exec(commond);
        logger.debug("系统命令【{}】执行完毕！", commond);
    }

    /**
     * 不可更改的{@code HashMap}对象。
     * <p>
     *     <b>说明：</b>该类提供了关闭put和putAll功能，根据需要如果确定{@code map}在之后不可更改其内容后则调用{@code closeChange()}。
     * </p>
     * @param <K> key
     * @param <V> value
     */
    private static final class UnChangeHashMap<K,V> extends HashMap<K,V> {

        private static final String ERROR_MESSAGE = "【UnChangeHashMap】对象内容不能被更改！";

        private boolean canChange = true;

        @Override
        public V put(K key, V value) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            super.putAll(m);
        }

        @Override
        public V remove(Object key) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.remove(key);
        }

        @Override
        public void clear() {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            super.clear();
        }

        @Override
        public boolean remove(Object key, Object value) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.remove(key, value);
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.replace(key, oldValue, newValue);
        }

        @Override
        public V replace(K key, V value) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.replace(key, value);
        }

        @Override
        public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            return super.merge(key, value, remappingFunction);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            if (!canChange) {
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            }
            super.replaceAll(function);
        }

        /**
         * 关闭Map对象内容改变功能，一旦该方法调用后，此map对象内容将不能被更改。
         */
        public void closeChange() {
            this.canChange = false;
        }
    }

}

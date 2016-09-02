package com.xinyuan.haze.common.utils;

import com.xinyuan.haze.core.spring.utils.SpringContextUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Java类处理工具类
 * Created by Sofar on 2016/8/30.
 */
public class HazeClassUtils extends ClassUtils {

    private static PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private static MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    /**
     * 获取指定路径下某个类或接口所有的子类信息
     * @param parentClass 指定类或接口
     * @param scanPackage 需要扫描的顶级包名称
     * @return 所有子类类名称集合列表
     */
    public static List<String> getSubclassNames(Class<?> parentClass, String scanPackage) throws Exception {
        String resourcePattern = "**/*.class";
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                org.springframework.util.ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(scanPackage)) + "/" + resourcePattern;
        Set<String> classNames = new HashSet<>();
        try {
            Resource[] resources = SpringContextUtils.getResources(packageSearchPath);
            for (org.springframework.core.io.Resource r : resources) {
                MetadataReader reader = metadataReaderFactory.getMetadataReader(r);
                String className = reader.getClassMetadata().getClassName();
                if (parentClass.isAssignableFrom(Class.forName(className)) && parentClass != Class.forName(className)) {
                    classNames.add(className);
                }
            }
            return new ArrayList<>(classNames);
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }
    }
}

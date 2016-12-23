package com.xinyuan.haze.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.env.DefaultEnvironment;
import org.junit.Test;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by sofar on 16-1-22.
 */
public class PoiTest {

    public void testProcess() throws Exception {
        String basePackage = "com.xinyuan";
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + "/" + "**/*.class";
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        MetadataReaderFactory metadataReaderFactory =
                new CachingMetadataReaderFactory(new PathMatchingResourcePatternResolver());
        for (Resource resource : resources) {

            ClassMetadata classMetadata = metadataReaderFactory.getMetadataReader(resource).getClassMetadata();
            String className = classMetadata.getClassName();
            System.out.println(ClassUtils.convertResourcePathToClassName(resource.getURL().getPath()));
            ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReaderFactory.getMetadataReader(resource));
            sbd.setResource(resource);
            sbd.setSource(resource);
        }
    }

    private String resolveBasePackage(String basePackage) {

        return ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
    }
}

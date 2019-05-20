package com.brotherj.brotherjserver.GenerateController.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * description：
 *
 * @author wangjie
 */
@Component
public class Test implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof  Test222){
//            ClassPool pool =ClassPool.getDefault();
//            try {
//                CtClass ctClass = pool.get(bean.getClass().getCanonicalName());
//                CtMethod[] methods = ctClass.getDeclaredMethods();
//                for (CtMethod method:methods){
//                    ParameterAnnotationsAttribute attribute = (ParameterAnnotationsAttribute) method.getMethodInfo().getAttribute(ParameterAnnotationsAttribute.visibleTag);
//                    Annotation[][] annotations = attribute.getAnnotations();
//                    for (int i=0;i<annotations.length;i++){
//                        for (int j=0;i<annotations[i].length;j++){
//                            System.out.println(annotations[i][j]);
//                        }
//                    }
//                    System.out.println(111);
//                }
//
//            } catch (NotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        return bean;
    }


//    /**
//     * 创建动态代理
//     *
//     * @param annotatedBeanDefinition
//     * @return
//     */
//
//    public Class<?> createProxy(AnnotatedBeanDefinition annotatedBeanDefinition) throws Exception {
//
//        AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
//        Class<?> target = Class.forName(metadata.getClassName());
//        String className= target.getSimpleName()+"Controller";
//        String path=target.getPackage().getName();
//
//        ClassPool pool =ClassPool.getDefault();
//        CtClass clazz = pool.makeClass(new StringBuilder().append(path).append(".").append(className).toString());
//        ClassFile ccFile = clazz.getClassFile();
//        ConstPool constpool = ccFile.getConstPool();
//
//        DataExchangeClients dataExchangeClients = target.getAnnotation(DataExchangeClients.class);
//
//        // 类附上注解
//        //类上的path来源于DataExchangeClients注解的value值
//        AnnotationsAttribute classAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//        Annotation controller = new Annotation("org.springframework.web.bind.annotation.RestController",constpool);
//        Annotation requestMapping = new Annotation("org.springframework.web.bind.annotation.RequestMapping",constpool);
//        ArrayMemberValue value1Array=new ArrayMemberValue(constpool);
//        value1Array.setValue(new StringMemberValue[]{new StringMemberValue(dataExchangeClients.value(),constpool)});
//        requestMapping.addMemberValue("value",value1Array);
//        classAttr.addAnnotation(controller);
//        classAttr.addAnnotation(requestMapping);
//        ccFile.addAttribute(classAttr);
//
//        //将service注入到controller中
//        CtClass service = pool.get(target.getCanonicalName());
//        String fieldName=target.getSimpleName();
//        // 增加字段
//        CtField field = new CtField(service,fieldName,clazz);
//        field.setModifiers(Modifier.PRIVATE);
//        FieldInfo fieldInfo = field.getFieldInfo();
//        // 属性附上注解
//        AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//        Annotation autowired = new Annotation("org.springframework.beans.factory.annotation.Autowired",constpool);
//        fieldAttr.addAnnotation(autowired);
//        fieldInfo.addAttribute(fieldAttr);
//        clazz.addField(field);
//
//        Method[] declaredMethods = target.getDeclaredMethods();
//        for (Method method:declaredMethods){
//            DataExchangeClient dataExchangeClient = method.getAnnotation(DataExchangeClient.class);
//            String methodName=method.getName();
//            Class<?>[] parameters = method.getParameterTypes();
//            CtClass[] ctClasses=new CtClass[parameters.length];
//            for (int i=0;i<parameters.length;i++){
//                ctClasses[i]=pool.get(parameters[i].getCanonicalName());
//            }
//            Class<?> returnType = method.getReturnType();
//            CtClass response=pool.get(returnType.getCanonicalName());
//            StringBuilder methodBody = new StringBuilder();
//            CtMethod ctMethod=new CtMethod(response,methodName, ctClasses,clazz);
//            ctMethod.setModifiers(Modifier.PUBLIC);
//            methodBody.append("{return ").append(fieldName).append(".").append(methodName).append("(");
//            StringBuilder returnMethod = new StringBuilder();
//            for (int i=1;i<=parameters.length;i++){
//                returnMethod.append("$").append(i).append(",");
//            }
//            methodBody.append(returnMethod.toString().substring(0,returnMethod.length()-1));
//            methodBody.append(");}");
//            ctMethod.setBody(methodBody.toString());
//
////            CtMethod ctMethod = CtNewMethod.make(methodBody.toString(),clazz);
//            //参数上加注解
//            /***
//             *   参数注解生成流程：
//             1、 创建注解二维数组Annotation[][]：第一维对应参数序列，第二维对应注解序列；
//             2、 参数注解属性ParameterAnnotationsAttribute添加注解二维数组Annotation[][]；
//             3、 方法信息CtMethod.getMethodInfo()添加参数注解属性ParameterAnnotationsAttribute。
//             */
//            Annotation[][] paramAnnotanArr = new  Annotation[1][1];
//            Annotation paramAnno = new Annotation("org.springframework.web.bind.annotation.RequestBody", constpool);
//            //paramAnno.addMemberValue("name", new StringMemberValue("name",constpool));
//            paramAnnotanArr[0][0]=paramAnno;
//            ParameterAnnotationsAttribute paramAnnoTan = new ParameterAnnotationsAttribute(constpool, ParameterAnnotationsAttribute.visibleTag);
//            paramAnnoTan.setAnnotations(paramAnnotanArr);
//
//
//            //方法附上注解
//            AnnotationsAttribute methodAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
//            ArrayMemberValue valueArray1=new ArrayMemberValue(constpool);
//            EnumMemberValue enumMemberValue = new EnumMemberValue( constpool);
//            enumMemberValue.setType("org.springframework.web.bind.annotation.RequestMethod");
//            enumMemberValue.setValue(String.valueOf(dataExchangeClient.method()));
//            valueArray1.setValue(new EnumMemberValue[]{enumMemberValue});
//            Annotation mapping = new Annotation("org.springframework.web.bind.annotation.RequestMapping",constpool);
//            ArrayMemberValue valueArray=new ArrayMemberValue(constpool);
//            valueArray.setValue(new StringMemberValue[]{new StringMemberValue(dataExchangeClient.path(),constpool)});
//            mapping.addMemberValue("value",valueArray);
//            mapping.addMemberValue("method",valueArray1);
//            methodAttr.addAnnotation(mapping);
//            MethodInfo info = ctMethod.getMethodInfo();
//            info.addAttribute(methodAttr);
//            info.addAttribute(paramAnnoTan);
//            clazz.addMethod(ctMethod);
//        }
////        String writePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
////        clazz.writeFile(writePath);
//        return clazz.toClass();
//    }

}

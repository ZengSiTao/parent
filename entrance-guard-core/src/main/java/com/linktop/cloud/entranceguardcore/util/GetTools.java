package com.linktop.cloud.entranceguardcore.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTools {
	public static final DecimalFormat NUM_FORMAT=new DecimalFormat("0.00");
	public static final String DATE_FORMAT = "yyyy年MM月dd日";

	/**
	 * 生成GUID 全球唯一的ID At:包杰磊
	 * @return 返回生成的GUID：string
	 */
	public static final String GetGUID() {
		UUID uuid = UUID.randomUUID();
		String ruid = "";
		if (uuid.toString().length() <= 32) {
			ruid = uuid.toString();
		} else {
			ruid = uuid.toString().replace("-", "");
		}
		return ruid;
	}

	/**
	 * Date数据类型转换成Timestamp类型At 杨正辉
	 * @param data Date数据类型值
	 * @return 返回Timestamp类型值
	 */
	public static final Timestamp date2TimesTamp(Date data) {
		return new Timestamp(data.getTime());
	}
	
	/**将date对象拼装成一个字符串  辜栋利
	 * @param date date对象；format  null默认格式方式yyyy-MM-dd HH:mm:ss
	 * @return 时间字符串
	 */
	public static String dateTstr(Date date, String format){
		return DateFormatUtils.format(date, format==null?"yyyy-MM-dd HH:mm:ss":format);
	}
	/**将字符串转date 
	 * @param 时间字符串；format null默认格式方式yyyy-MM-dd HH:mm:ss
	 * @return date 
	 * @throws ParseException
	 */
	public static Date strTdate(String date, String format) {
		try {
			return DateUtils.parseDate(date, format==null?"yyyy-MM-dd HH:mm:ss":format);
		} catch (ParseException e) {
			e.printStackTrace();return null;
		}
	}
	/**
	 * @return 当前日期格式 "yyyy-MM-dd HH:mm:ss"
	 */
	 public static String getNowDateTime(){
		 return dateTstr(new Date(), null);
	 }
	 
	 public static Timestamp String2Timestamp(String datetime){
		 try {
			return  new Timestamp(strTdate(datetime, null).getTime());
		} catch (Exception e) {
			e.printStackTrace(); return null;
		}
	 }
	 
	 /**
	  * 计算两个日期的时间差,返回相差的秒数AT 杨正辉
	  * @param formatTime1
	  * @param formatTime2
	  * @return
	  */
	 public static int getTimeDifference2sec(Timestamp formatTime1, Timestamp formatTime2) {
		 return (int)(formatTime1.getTime()-formatTime2.getTime())/1000;
		 }
	 /**
	  * 计算两个日期的时间差,返回相差的小时数 AT 杨正辉
	  * @param formatTime1
	  * @param formatTime2
	  */
	 public static int getTimeDifference(Timestamp formatTime1, Timestamp formatTime2) {
		 return getTimeDifference2sec(formatTime1,formatTime2)/3600;
	 }
	 
	 /**按longNum随机生成数字长库
	 * @param longNum长度，如1.
	 * @return字符串
	 */
	public static String getStrBitRan(int longNum) {
		return ""+randomInt(longNum);
	 }
	 
    /**
     * 取得SpringBean实例
     * @param request
     * @param beanId
     * @return
     */
    public static Object getBean(HttpServletRequest request, String beanId) {
     WebApplicationContext ctx = WebApplicationContextUtils
  .getRequiredWebApplicationContext(request.getSession().getServletContext());
        return ctx.getBean(beanId);
    }
    
    /**
     * 对新增实体保存时需要增加当前时间
     * 后期可以扩展
     * @param
     * @return
     */
    public static void setBeanProperty(Object bean) {
    	try {
    		Class<?> cls =bean.getClass();
    		Field field = cls.getDeclaredField("createtime");
    		field.setAccessible(true);
    		field.set(bean, new Timestamp(System.currentTimeMillis()));
		}  catch (Exception e) {
			e.printStackTrace();
		}
    }

	public static int randomId() { 
		return  Math.abs((int) System.currentTimeMillis());
    }

    /**
     * 对前台传参数解析 格式 coursename=a&&b=b&&c=c;
     * @param String  @return Map<String, Object>
     */
	public static Map<String, Object> analyParam(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] a = param.split("&&");
		for (String str : a) {
			String[] b = str.split("=");
			try{
			map.put(b[0], b[1]);
			}catch (Exception e) {
				map.put(b[0], "");
			}
		}
		return map;
	}
    /**
     * 将同类型对象A复制到B中
     * @author Administrator 辜栋利
     * @param Object source, Object target
     */
	public static void copyProperties(Object source, Object target) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	 /**
     * 将同类型对象A中不为空的属性，复制到B中
     * @param Object source, Object target
     */
	public static void beanToBean(Object source, Object target) {
		try{
			if(!source.getClass().getName().equals(target.getClass().getName())){
				throw new Exception("不同类型的数据不能进行复制");
			}
			Field[] fields = source.getClass().getDeclaredFields();
			Class<?> targetcls =target.getClass();
			for (Field field : fields) {
				if(Modifier.isStatic(field.getModifiers()))continue ;
				field.setAccessible(true);
				Object value = field.get(source);
				if(value!=null){
					Field f = targetcls.getDeclaredField(field.getName());
					f.setAccessible(true);
					f.set(target, value);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * 将对象中为空的设置默认为‘’或者0，设置当前时间
     * @param Object source
     */
	public static void setdefault(Object source) {
		try {
			Class<?> cls = source.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				if(Modifier.isStatic(field.getModifiers()))continue ;
				field.setAccessible(true);
				Object value = field.get(source);
				if(value!=null)continue ;
				String simplename =field.getType().getSimpleName();
				if(simplename.equals("String")){
					field.set(source, "");
				}else if(simplename.equals("Timestamp")){
					field.set(source, new Timestamp(System.currentTimeMillis()));
				}else if(simplename.equals("Date")){
					field.set(source, new Date(System.currentTimeMillis()));
				}else if(simplename.equals("Double")){
					field.set(source, 0.0);
				}else if(simplename.equals("Integer")){
					field.set(source, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 /**
     *	将文件更名 路径必须是绝对路径
     * @author Administrator 辜栋利
     * @param String f1  f2
     */
	public  static void file2newFile(String f1 , String f2){
		new File(f1).renameTo(new File(f2));
	}
	
	/**
	 * 将字符串转为 ISO88591 标准
	 * @author 姜磊
	 * @param str
	 * @return
	 */
	public static String toISO88591(String str) {
		try {
			str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return str;
	}

	/**
	 * 截取字符串
	 * @param str 字符串
	 * @param width 截取长度
	 * @param end 结尾替代符
	 */
	public static String doSubstr(String str, int width, String end) {
		str = StringUtils.substring(str, 0,width);
		return str.length()>(width-end.length())?(StringUtils.substring(str, 0,width-end.length())+end):str;
	}
	/**
	 * 过滤HTML标签
	 * @author 姜磊
	 * @param inputString HTML字符串
	 * @return
	 */
	public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {  
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);  
            htmlStr = m_script.replaceAll("");
  
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);  
            htmlStr = m_style.replaceAll("");
  
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);  
            htmlStr = m_html.replaceAll("");
  
            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);  
            htmlStr = m_html1.replaceAll("");
            textStr = htmlStr;  
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }  
        return textStr;
    }
	
	/**
	 * 文件名转中文，格式不变
	 */
	public static String nametoPinYin(String path){
		return toPinYin( FilenameUtils.getBaseName(path))+"."+ FilenameUtils.getExtension(path);
	}
	/**
	 * 将中文转拼音 特殊字符会过滤掉
	 * @param String
	 */
	public static String toPinYin(String chinese){
		chinese = chinese.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5]","");
		StringBuilder sb = new StringBuilder();
		char[] charArray = chinese.toCharArray();
		//根据需要定制输出格式，默认的即可
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE  );//设置声调格式
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V );//设置特殊拼音ü的显示格式
		try {  
			for (char c : charArray) {
				if (c > 128) {//遍历数组，ASC码大于128进行转换
	        		sb.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
	        	}else{
	        		sb.append(c);
	            }
			}
	        return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "a";
		}
	 }
	
	/** 
	* 将汉字转换为全拼  At杨正辉
	* @param src 要转换的汉字字符串
	* @return String返回转换后的汉语语拼音 
	*/ 
	public static String getPinYin(String src) {
		char[] t1 = src.toCharArray(); 
		// 设置汉字拼音输出的格式 
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat(); 
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
		t3.setVCharType(HanyuPinyinVCharType.WITH_V); 
		StringBuilder sb = new StringBuilder();
		try { 
			for (char c : t1) {
				if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
					String[] t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);// 将汉字的几种全拼都存到t2数组中
					sb.append(t2[0]);// 取出该汉字全拼的第一种读音并连接到字符串t4后 
				} else { // 如果不是汉字字符，直接取出字符并连接到字符串t4后 
					sb.append(c);
				} 
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) { 
			e.printStackTrace(); 
		} 
		return sb.toString(); 
	} 
	/** 
	* 提取每个汉字的首字母  At杨正辉
	* @param src 要转换的汉字字符串
	* @return String返回每个汉字的首字母 
	*/ 
	public static String getPinYinHeadChar(String str) {
		StringBuilder sb = new StringBuilder();
		int n = str.length();
		for (int j = 0; j < n; j++) { 
			char word = str.charAt(j); 
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) { 
				sb.append(pinyinArray[0].charAt(0));
			} else { 
				sb.append(word);
			} 
		} 
		return sb.toString(); 
	} 
	
	/**
	 * 在指定目录下创建文件夹
	 * @param path 文件夹创建路径
	 * @param name 文件夹名称
	 */
	public static boolean MakeDir(String path, String name){
		try {
			if(!new File(path).isDirectory()) return false;
			FileUtils.forceMkdir(new File(path+"/"+name));return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 将文件大小转换为KB或MB
	 * @param size @return String
	 */
	public static String getFileSizeString(long size){
		DecimalFormat df = new DecimalFormat("###.##");
		if (size < 1024){
			return size+"B";
		}else if ( size < 1048576) {
		    return df.format((float)size/1024)+"KB";
		}else if ( size < 1073741824){
		    return df.format((float)size/1048576)+"MB";
		}else{
		    return df.format((float)size/1073741824)+"G";
		}
	}
	/**
	 * 验证选项非法字符
	 * @author zhangtao 
	 * @param String
	 * @return String
	 */
	public static String correctionOption(String s){
//		s = StringUtils.replace(s, "\"", "\\\\\"");
//		s = StringUtils.replace(s, "<", "&gt;");
//		s = StringUtils.replace(s, ">", "&gt;");
//		s = StringUtils.replace(s, "\n", "");
//		s = StringUtils.replace(s, "\r", "");
		s= StringEscapeUtils.escapeHtml4(s);
		return s;//StringUtils.replace(s, "\t", "");
		/*String option=word;
		String regx="";
		String mes="";
		for(int i =0;i<6;i++){
			switch (i) {
			case 0:
				regx="\"";
				mes="\\\\\"";
				break;
			case 1:
				regx="<";
				mes="&lt;";
				break;
			case 2:
				regx=">";
				mes="&gt;";
				break;
			case 3:
				regx="\n";
				mes="";
				break;
			case 4:
				regx="\r";
				mes="";
				break;
			case 5:
				regx="\t";
				mes="";
				break;
			}

			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(option);
			StringBuffer sbr = new StringBuffer();
			while (matcher.find()) {
			    matcher.appendReplacement(sbr, mes);
			}
			matcher.appendTail(sbr);
			option = sbr.toString();
		}
		return option;*/
	}
	/**2012-11-07T00:00:00的后一天
	 * @param date
	 * @return
	 */
	public static String strnextlong(String date){
   		Timestamp ts = Timestamp.valueOf(date.toString().replace("T", " "));
   		return dateTstr(DateUtils.addDays(new Date(ts.getTime()), 1), null);
	}
	/**zhangtao
	 * 生成纯数字id号
	 * @return string
	 */
	public static String makenums(){
		return dateTstr(new Date(), "yyyyMMddHHmmssSSS")+"00"+ Math.round(Math.random()*899+100);
	}
	/** 
	 * 生成随机几位数
	 * @param n @return int
	 */
	public static int randomInt(int n) {
		double a = Math.random();
		return (int) (Math.pow(10, n- Math.ceil(Math.log10(a)))*a);
	}
	/**
	 * MD5加密
	 */
	public static String getMD5(String str) {
		return DigestUtils.md5Hex(str);
    }
	
	 /**  
	  *  判断某个字符串是否存在于数组中
	  *  @param stringArray 原数组
	  *  @param source 查找的字符串
	  *  @return 是否找到
	  */
	public static boolean getContains(String[] stringArray, String source) {
		return ArrayUtils.contains(stringArray, source);
	} 
	  /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale, int roundingMode) {   
        return new BigDecimal(Double.toString(value)).setScale(scale, roundingMode).doubleValue();
    }   
	  /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  默认四舍五入
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale) {   
    	return new BigDecimal(Double.toString(value)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /** 
     * double 相加 保证精度
     * @param scale 精度 
     * @param values 多个参数 
     */ 
    public static double numAdd(int scale,double... values){ 
    	BigDecimal sum = new BigDecimal("0");
    	for (double d : values) {
    		sum=sum.add(new BigDecimal(Double.toString(d)));
		}
    	return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
    /** 
     * double 相减 保证精度
     * @param scale 精度 
     * @param values 多个参数  mvalue 被减数
     */ 
    public static double numSub(int scale,double mvalue,double... values){ 
    	BigDecimal sum = new BigDecimal(Double.toString(mvalue));
    	for (double d : values) {
    		sum=sum.subtract(new BigDecimal(Double.toString(d)));
		}
    	return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
    
    /** 
     * double 相乘 保证精度
     * @param scale 精度 
     * @param values 多个参数  
     */ 
    public static double numMul(int scale,double... values){ 
    	BigDecimal sum = new BigDecimal("1");
    	for (double d : values) {
    		sum=sum.multiply(new BigDecimal(Double.toString(d)));
		}
    	return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
    
    /** 
     * double 相除 保证精度
     * @param scale 精度 
     * @param values 多个参数  mvalue 被除数
     */ 
    public static double numDiv(int scale,double mvalue,double... values){ 
    	BigDecimal sum = new BigDecimal(Double.toString(mvalue));
    	for (double d : values) {
    		sum=sum.divide(new BigDecimal(Double.toString(d)),scale+1, BigDecimal.ROUND_HALF_UP);
		}
    	return sum.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
    
     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue(); 
    } 

    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     */ 
    public static double div(double d1,double d2,int scale){ 
        //  当然在此之前，你要判断分母是否为0， 为0你可以根据实际需求做相应的处理 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    } 
	
    /**
     * 匹配字符串
     * @param matcher 被匹配字符串
     * @param pattern	匹配规则
     */
    public static boolean matcherPattern(String matcher, String pattern){
         return Pattern.compile(pattern).matcher(matcher).matches();
    } 
    /**
     * SQL使用LIKE查询时特殊字符处理
     * @param str  @return str
     */
    public static String correctLikeQuerywords (String str) {
    	String new_str = str.replace("'", "''").replace("/","//").replace("%", "/%").replace("_", "/_");
    	return new_str;	    
    }
    
    public final static String FORMAT_NUM ="%.2f";
    /**
     * 数字保留两位小数字符串
     * @param   @return str
     */
    public static String numTstr (Object num) {
    	return String.format(FORMAT_NUM, num);
    }
    
    /**
	 * Object 转string 数字格式，日期格式化
	 * @param obj
	 * @return
	 */
	public static String objTstr(Object obj ){
			String r ="";
		   if (obj instanceof Number) {
			   r = NUM_FORMAT.format(obj);
		   } else if (obj instanceof String) {
		    r=obj.toString();
		   } else if (obj instanceof Date) {
			   Date d=(Date) obj;
			   r=GetTools.dateTstr(d, DATE_FORMAT);
		   }else if(obj==null){
			   r="";
		   }
		   return r;
	}
	//获得项目
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	/**
	 * UNICODE码转中文
	 * @param str
	 * @return
	 */

	public static String unicodeToString(String str) {
		String sg = "\\u";
		int a = 0;
		List<String> list = new ArrayList<>();
		while (str.contains(sg)) {
			str = str.substring(2);
			String substring;
			if (str.contains(sg)) {
				substring = str.substring(0, str.indexOf(sg));
			} else {
				substring = str;
			}
			if (str.contains(sg)) {
				str = str.substring(str.indexOf(sg));
			}
			list.add(substring);
		}
		StringBuffer sb = new StringBuffer();
		if (!CollectionUtils.isEmpty(list)){
			for (String string : list) {
				sb.append((char) Integer.parseInt(string, 16));
			}
		}
		return sb.toString();
	}


	public static void main(String[] args) {
		System.out.println(getPorjectPath());
	}
}

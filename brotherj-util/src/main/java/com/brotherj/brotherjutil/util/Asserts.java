package com.brotherj.brotherjutil.util;

import com.brotherj.brotherjutil.ex.AssertsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Asserts {

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new AssertsException(message);
		}
	}
	public static void isNull(Object object, ResultCodeEnum codeEnum) {
		if (object != null) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isNotNull(Object object, ResultCodeEnum codeEnum) {
		if (object == null) {
			throw new AssertsException(codeEnum);
		}
	}


	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new AssertsException(message);
		}
	}

	public static void isTrue(boolean expression, ResultCodeEnum codeEnum) {
		if (!expression) {
			throw new AssertsException(codeEnum);
		}
	}


	public static void isTrue(boolean expression, String code, String message) {
		if (!expression) {
			throw new AssertsException(code,message);
		}
	}


	public static void isNotTrue(boolean expression, ResultCodeEnum codeEnum) {
		if (expression) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isNotEmpty(String param, String message) {
		if (param == null || param.trim().length() <= 0) {
			throw new AssertsException(message);
		}
	}

	public static void isEqualTypeIsBigDecimal(BigDecimal param, BigDecimal param2, String message) {
		if (param.compareTo(param2) != 0) {
			throw new AssertsException(message);
		}
	}

	public static void isNotNull(Object object,String code, String message) {
		if (object == null) {
			throw new AssertsException(code,message);
		}
	}

	public static void isNotNull(Object object, String message) {
		if (object == null) {
			throw new AssertsException(message);
		}
	}



	public static void isNotNullOrEmpty(Object object, String message) {
		if (object == null || "".equals(object)) {
			throw new AssertsException(message);
		}
	}

	public static void isNotNullOrEmpty(Object object, ResultCodeEnum codeEnum) {
		if (object == null || "".equals(object)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isNotNull(String message, Object... objects) {
		if (objects == null)
			throw new AssertsException(message);
		for (Object object : objects)
			if (object == null)
				throw new AssertsException(message);
	}

	public static void isNotEmptyStr(String message, String... strs) {
		if (strs == null)
			throw new AssertsException(message);
		for (String str : strs)
			if (str == null || str.equals(""))
				throw new AssertsException(message);
	}

	public static void isNotEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new AssertsException(message);
		}
	}

	public static void isNotEmpty(Collection<?> collection, ResultCodeEnum codeEnum) {
		if (collection == null || collection.isEmpty()) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isNotScope(Collection<?> collection, Integer min, Integer max, ResultCodeEnum codeEnum) {
		if (collection.size() < min && collection.size() > max) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isSingleElementCollection(Collection<?> collection) {

		if (collection == null) {
			throw new AssertsException("this collection is not a single element collection. collection is null");
		}

		if (collection.size() != 1) {
			throw new AssertsException("this collection is not a single element collection. size=" + collection.size());
		}
	}

	public static void isNotNullString(String str, String message) {
		if (str == null || Objects.equals(str.trim(), "")) {
			throw new AssertsException(message);
		}
	}

	public static void isTrimLength(String str, Integer min, Integer max, String message) {
		if (str == null || str.trim().length() < min || str.trim().length() > max) {
			throw new AssertsException(message);
		}
	}

	public static void isTrimLength(String str, Integer min, Integer max, ResultCodeEnum codeEnum) {
		if (str == null || Objects.equals(str.trim(), "") || str.trim().length() < min || str.trim().length() > max) {
			throw new AssertsException(codeEnum);
		}
	}



	public static void isLength(Integer val, Integer min, Integer max, ResultCodeEnum codeEnum) {
		if (val == null || val < min || val > max) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isTrimStartWith(String str, String prefix, ResultCodeEnum codeEnum) {
		if (str.trim().startsWith(prefix)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isTrimEndWith(String str, String suffix, ResultCodeEnum codeEnum) {
		if (str.trim().endsWith(suffix)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static <T> void isIn(T val, List<T> valueScope, String message) {
		if (val == null || !valueScope.contains(val)) {
			throw new AssertsException(message);
		}
	}

	public static <T> void isIn(T val, List<T> valueScope, ResultCodeEnum codeEnum) {
		if (val == null || !valueScope.contains(val)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static <T> void mayIn(T val, List<T> valueScope, String message) {
		if (val == null) {
			return;
		}

		if (!valueScope.contains(val)) {
			throw new AssertsException(message);
		}
	}

	public static <T> void isNotIn(T val, List<T> valueScope, String message) {
		if (val == null || valueScope.contains(val)) {
			throw new AssertsException(message);
		}
	}

	public static <T> void isRepeat( List<T> valueScope, ResultCodeEnum codeEnum) {
		if (valueScope.stream().collect(Collectors.toSet()).size() !=(valueScope.size())) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isEqual(Object obj1, Object obj2, String message) {
		if (!Objects.equals(obj1, obj2)) {
			throw new AssertsException(message);
		}
	}

	public static void compareEqual(BigDecimal b1, BigDecimal b2, String message) {
		if (b1.compareTo(b2) != 0) {
			throw new AssertsException(message);
		}
	}

	public static void compareEqual(BigDecimal b1, BigDecimal b2, ResultCodeEnum codeEnum) {
		if (b1.compareTo(b2) != 0) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void moreThan(BigDecimal b1, BigDecimal b2, ResultCodeEnum codeEnum) {
		if (b1.compareTo(b2) > 0) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void lessThan(BigDecimal b1, BigDecimal b2, ResultCodeEnum codeEnum) {
		if (b1.compareTo(b2) < 0) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void lessEqualAndThan(BigDecimal b1, BigDecimal b2, ResultCodeEnum codeEnum) {
		if (b1.compareTo(b2) <= 0) {
			throw new AssertsException(codeEnum);
		}
	}
	public static void compareScale(BigDecimal b1, Integer scale, ResultCodeEnum codeEnum) {
		if (b1.scale() != scale) {
			throw new AssertsException(codeEnum);
		}
	}



	public static void isNotEqual(Object obj1, Object obj2, String message) {
		if (Objects.equals(obj1, obj2)) {
			throw new AssertsException(message);
		}
	}

	public static void mayEqual(Object obj1, Object obj2, String message) {
		if (obj1 == null) {
			return;
		}

		if (!Objects.equals(obj1, obj2)) {
			throw new AssertsException(message);
		}
	}

	public static void mayEqual(Object obj1, Object obj2, ResultCodeEnum codeEnum) {
		if (obj1 == null) {
			return;
		}

		if (!Objects.equals(obj1, obj2)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isPattern(String input, String regex, String message) {
		if (!Pattern.matches(regex, input)) {
			throw new AssertsException(message);
		}
	}

	public static void isNotBlank(String str, String message) {
		if (str == null || Objects.equals(str.trim(), "")) {
			throw new AssertsException(message);
		}
	}

	public static void isNotBlank(String str, ResultCodeEnum codeEnum) {
		if (str == null || Objects.equals(str.trim(), "")) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void isBlank(String str, ResultCodeEnum codeEnum) {
		if (str != null && str.length()>0) {
			throw new AssertsException(codeEnum);
		}
	}

//	public static void isNotMax(String str, Integer maxLength, String message) {
//		if (str == null || str.trim().length() > maxLength) {
//			throw new AssertsException(LinkUtil.link(message, "长度不能超过", maxLength, "个字符"));
//		}
//	}
//
//	public static void mayNotMax(String str, Integer maxLength, String message) {
//		if (StringUtil.isEmpty(str)) {
//			return;
//		}
//
//		if (str.trim().length() > maxLength) {
//			throw new AssertsException(LinkUtil.link(message, "长度不能超过", maxLength, "个字符"));
//		}
//	}


	public static void mayScope(BigDecimal val, Integer start, Integer end, String message) {
		if (val == null) {
			return;
		}

		if (val.doubleValue() < start.doubleValue() || val.doubleValue() > end.doubleValue()) {
			throw new AssertsException(message+ "必须大于等于"+start+ "并且小于等于"+end);
		}
	}

	public static void inScope(BigDecimal height, Integer start, Integer end, String message) {
		if (height == null || height.doubleValue() < start.doubleValue() || height.doubleValue() > end.doubleValue()) {
			throw new AssertsException(message+ "必须大于等于"+start+"并且小于等于"+ end);
		}
	}

	public static void isAfter(LocalDate startTime, LocalDate endTime, String message) {
		if (startTime.isAfter(endTime)) {
			throw new AssertsException(message);
		}
	}

	public static void isBefore(Date startTime, Date endTime, ResultCodeEnum codeEnum) {
		if (startTime.before(endTime)) {
			throw new AssertsException(codeEnum);
		}
	}

	public static void mayAfter(LocalDate startTime, LocalDate endTime, String message) {
		if (startTime == null || endTime == null) {
			return;
		}

		if (startTime.isAfter(endTime)) {
			throw new AssertsException(message);
		}
	}

	public static void isAfter(LocalDateTime startTime, LocalDateTime endTime, String message) {
		if (startTime.isAfter(endTime)) {
			throw new AssertsException(message);
		}
	}

	public static void mayAfter(LocalDateTime startTime, LocalDateTime endTime, String message) {
		if (startTime == null || endTime == null) {
			return;
		}

		if (startTime.isAfter(endTime)) {
			throw new AssertsException(message);
		}
	}

	public static <T, R> void isValidBatch(List<T> paramList, Function<R, T> resultFunc, List<R> resultList, String message) {
		List<T> resultKeyList = new ArrayList<>();

		for (R result : resultList) {
			resultKeyList.add(resultFunc.apply(result));
		}

		List<T> notValidParamList = new ArrayList<>();

		for (T param : paramList) {
			if (resultKeyList.contains(param)) {
				continue;
			}
			notValidParamList.add(param);
		}

		if (!notValidParamList.isEmpty()) {
			throw new AssertsException(message + ",非法参数为" + notValidParamList);
		}
	}


    public static void isNotNullAndSizeIsNotZero(Collection<?> collection, String message) {
        if (collection != null && !collection.isEmpty()) {
            throw new AssertsException(message);
        }
    }




}

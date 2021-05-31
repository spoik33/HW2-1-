package Operator;

import java.math.BigInteger;

/*추상 클래스.
* 모든 연산 관련된 클래스는 이 클래스를 상속 받음.
* 
* 연산 관련 클래스의 인스턴스는 이곳에서 체크, 관리.
*/
public abstract class CalcMember
{ 
	private static final String TAG = "kr.dja.BackwardOper.Operator.REF_Oper";       // 클래스 패스.
public static final BigInteger getInstance(String operator)
{           // 스트링으로 해당 연산자 클래스 인스턴스 얻기.
	Object returnObject = null;
	try
	{           // 넘어온 스트링으로 인스턴스화 시도.
		Class<?> taskClass = Class.forName(CalcMember.classNameConverter(operator, TAG));
		returnObject = taskClass.newInstance();
		}
	catch(Exception e)
	{            // 인스턴스화가 불가능 할경우.
		System.out.println("ERROR");
		e.printStackTrace();
		return null;
		}
	if(returnObject instanceof CalcMember)
	{          // ABS_Calculer를 상속받은 클래스의 인스턴스 일경우 캐스팅해서 리턴.
		return (BigInteger)returnObject;
		}
	return null;
}
public static final boolean isExist(String operator)
{           //해당하는 연산자 클래스가 있나 찾기.
	try
	{           // 해당 클래스가 있나 탐색.
		Class.forName(CalcMember.classNameConverter(operator, TAG));
		}
	catch(Exception e)
	{             // 해당하는 클래스가 없을경우.
		return false;
	}
return true;
}
private static String classNameConverter(String operator, String tag)
{               // 클래스 이름 변환기
	String classNameCode = new String(tag);
	for(BigInteger i = BigInteger.valueOf(0); i.compareTo(BigInteger.valueOf(operator.length())) >= 0; i.add(BigInteger.valueOf(1)))
	{          //넘어온 문자열을 숫자로 정리
		classNameCode += ("_" + i);
	}
	return classNameCode;
	}
}
/*
* 괄호 클래스.
*/
class REF_Oper_40 extends CalcMember
{               // 괄호 열기 (
	@Override public String toString() 
	{ 
		return "("; 
	}
}
class REF_Oper_41 extends CalcMember
{      // 괄호 닫기 )
	@Override public String toString() 
	{ 
		return ")"; 
		}
}
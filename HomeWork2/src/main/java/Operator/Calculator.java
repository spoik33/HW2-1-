package Operator;

import java.math.BigInteger;

/*
* ABS_Calculer 추상 클래스.
* 연산자는 해당 클래스를 상속 받도록 구현함.
*/
public abstract class Calculator extends CalcMember
{            // 연산자 추상 클래스.
	public abstract BigInteger getPriority();            // 우선순위 얻기 메서드.
	public abstract NumOperand task(NumOperand num1, NumOperand num2);          // 연산 메서드.
}

/*
* ABS_Calculer 추상 클래스를 상속받은 연산자 클래스.
* 형식: REF_Oper_[키 코드]
* 리플렉션으로만 접근.
*/


class REF_Oper_43 extends Calculator
{          // 더하기 클래스.
	@Override public BigInteger getPriority() { return BigInteger.valueOf(0); }
	@Override public NumOperand task(NumOperand num1, NumOperand num2)
	{ 
		return new NumOperand(num1.getValue().add(num2.getValue())); 
	}
	@Override public String toString() 
	{ 
		return "+"; 
	}
}
class REF_Oper_45 extends Calculator
{               // 빼기 클래스.
	@Override public BigInteger getPriority() { return BigInteger.valueOf(0); }
	@Override public NumOperand task(NumOperand num1, NumOperand num2)
	{ 
		return new NumOperand(num1.getValue().subtract(num2.getValue())); 
	}
	@Override public String toString() { 
		return "-"; 
	}
}
class REF_Oper_42 extends Calculator
{        // 곱하기 클래스.
	@Override public BigInteger getPriority() {
		return BigInteger.valueOf(1);
	}
	@Override public NumOperand task(NumOperand num1, NumOperand num2)
	{ 
		return new NumOperand(num1.getValue().multiply(num2.getValue())); 
		}
	@Override public String toString() 
	{ 
		return "*"; 
	}
}
class REF_Oper_47 extends Calculator
{           // 나누기 클래스.
	@Override public BigInteger getPriority() { return BigInteger.valueOf(1); }
	@Override public NumOperand task(NumOperand num1, NumOperand num2)
	{ 
		return new NumOperand(num1.getValue().divide(num2.getValue())); 
		}
	@Override public String toString() { return "/"; 
	}
}
class REF_Oper_37 extends Calculator
{  // 나머지 클래스.
	@Override public BigInteger getPriority() { return BigInteger.valueOf(1); }
	@Override public NumOperand task(NumOperand num1, NumOperand num2)
	{
		return new NumOperand(num1.getValue().remainder(num2.getValue())); 
		}
	@Override public String toString() { return "%"; 
	}
}
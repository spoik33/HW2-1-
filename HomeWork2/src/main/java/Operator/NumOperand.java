package Operator;

import java.math.BigInteger;

//피연산자 클래스
public class NumOperand extends CalcMember
{              // 숫자 피연산자
	private BigInteger value;
	public NumOperand(BigInteger taskInteger)
	{
		this.value = taskInteger;
	}
	public BigInteger getValue()
	{
		return this.value;
	}
	@Override public String toString() { return String.valueOf(this.value); }
}

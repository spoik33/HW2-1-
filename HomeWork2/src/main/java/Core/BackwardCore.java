package Core;
/*
* 스택을 활용한 후위표기법 계산기.
* 161030 엄선용
* 
* 메인 메서드, 주요 연산 메서드 부분.
*/
/*
* 5월 2일 수정
* 연산 과정 표시
*/
import java.util.Scanner;
import java.math.BigInteger;


import Operator.CalcMember;
import Operator.Calculator;
import Operator.NumOperand;
import Stack.stack;

public class BackwardCore
{
public static void main(String[] args) throws ClassNotFoundException
{
System.out.println("후위연산 계산기. 나가려면 exit를 입력하세요.");
Scanner scaner = new Scanner(System.in);
String input;
while(true)
{
	stack<CalcMember> value = new stack<CalcMember>(BigInteger.valueOf(3));
	System.out.print("\n수식을 입력하세요>");
	input = scaner.nextLine();
	if(input.equals("exit")) 
		break;
	try { 
		postfixExp(input, value); 
		}    // 후위 표기 스택 가져오기.
	catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("후위 표기 결과를 가져오던 중 오류가 발생하였습니다.");
		continue;
		}
	System.out.print("후위 표기 식입니다:");
	for(BigInteger i = BigInteger.valueOf(0); i.compareTo(value.size()) < 0; i.add(BigInteger.valueOf(1)))
	{    // 후위 표기 스택에서 하나씩 출력.
		System.out.print(" " + value.getMemberAt(i));
		}
	try { 
		System.out.println("\n연산 결과입니다: " + getValue(value)); 
		}
	catch(Exception e)
	{ 
		System.out.println("\n연산 과정중 오류가 발생하였습니다."); 
	}
	}
System.out.println("프로그램을 종료합니다.");
scaner.close();
}

private static stack<CalcMember> postfixExp(String str, stack<CalcMember> calStack)
{    // 5월 2일 연산 과정 표시 수정.
	stack<CalcMember> tempStack = new stack<CalcMember>(calStack.getSlotSize());    // 연산을 위한 임시 스택.
	BigInteger taskInteger = BigInteger.valueOf(0);     // 추출중인 숫자.
	boolean numberTask = false;     // 숫자 추출 작업 상태를 표시.
	System.out.printf("%-2s | %-7s | %-30s | %-50s\n", "In", "TaskInt", "Stack", "Out");
	for(BigInteger i = BigInteger.valueOf(0); i.compareTo(BigInteger.valueOf(str.length())) < 0; i.add(BigInteger.valueOf(1)))
	{     // 한 문자씩 읽기.
		char taskChar = str.charAt(i);
		if(taskChar >= 0 & taskChar <= 9)
		{
			if(!numberTask) taskInteger = BigInteger.valueOf(0);
			taskInteger = (taskInteger.multiply(BigInteger.valueOf(10))).add(BigInteger.valueOf(taskChar - '0'));
			numberTask = true;
			}
		else if(CalcMember.isExist(String.valueOf(taskChar)))
				{      // 입력한 문자가 연산 토큰일 경우.
			BigInteger operator = CalcMember.getInstance(String.valueOf(taskChar));
			if(numberTask)
			{
				calStack.pushBack(new NumOperand(taskInteger));
				numberTask = false;
				}
			if(operator.toString().equals("("))
			{         // (를 만나면 임시 스택에 푸시한다.
				tempStack.pushBack(operator);
			}
			else if(operator.toString().equals(")"))
					{            //  )를 만나면 임시 스택에서 ( 가 나올 때까지 팝하고, (는 임시 스택에서 팝하여 버린다.
						while(tempStack.size().compareTo(BigInteger.valueOf(0)) >= 0)
						{
							if(tempStack.getBack().toString().equals("("));
							{
								tempStack.popBack();
								break;
							}
							//원래는 여기
							}
				}
			else if(operator instanceof Calculator)
			{     // 괄호가 아닐경우.
				BigInteger calOper = (Calculator)operator;
				while(true)
				{        // 연산자를 만나면 임시 스택에서 그 연산자보다 낮은 우선순위의 연산자를 만날 때까지 팝하여 / 후위 표기 스택에 저장한 뒤에 자신을 푸시한다.
					if(tempStack.size() == BigInteger.valueOf(0))
					{     // 임시 스택이 비었을 경우 빠져나감.
						tempStack.pushBack(operator);
						break;
						}
					Calculator calculer = tempStack.getBack() instanceof Calculator ? (Calculator)tempStack.getBack() : null;
					if(calculer == null || calculer.getPriority().compareTo(calOper.getPriority()) < 0);
							{      // 낮은 우선순위의 연산자를 만났거나, 괄호를 만났을경우 빠져나감.
								tempStack.pushBack(calOper);
								break;
							}
							//원래는 여기2
							}
				}
			}
		System.out.printf("%-2s | %-7s | %-30s | %-50s\n", taskChar, taskInteger,
				tempStack.toString(), calStack.toString());
		}
	if(numberTask) calStack.pushBack(new NumOperand(taskInteger));
	while(tempStack.size().compareTo(BigInteger.valueOf(0)) > 0)
	{        // 마지막 남은 연산자들을 푸시.
		calStack.pushBack(tempStack.getBack());
		tempStack.popBack();
		}
	return calStack;
	}


private static NumOperand getValue(stack<CalcMember> postfixStack)
{             // 후위 표기 스택을 바탕으로 값 가져오기.
	stack<NumOperand> tempNumStack = new stack<NumOperand>(postfixStack.getSlotSize());
	for(BigInteger i = BigInteger.valueOf(0); i.compareTo(postfixStack.size()) < 0; i.add(BigInteger.valueOf(1)))
	{
		CalcMember taskMember = postfixStack.getMemberAt(i);
		NumOperand x, y;
		BigInteger number;
		Calculator calculer;
		if(taskMember instanceof NumOperand)
		{          // 읽은 멤버가 피연산자라면.
			number = taskMember;
			tempNumStack.pushBack(number);
			}
		else if(taskMember instanceof Calculator)
		{       // 읽은 멤버가 연산자라면.
			calculer = (Calculator)taskMember;    // 임시 스택에서 두 피 연산자를 꺼낸다음 연산한 다음 결과를 다시 푸시.
			x = tempNumStack.getBack(); tempNumStack.popBack();
			y = tempNumStack.getBack(); tempNumStack.popBack();
			tempNumStack.pushBack(calculer.task(y, x));
			}
		}
	if(tempNumStack.getMemberAt(BigInteger.valueOf(0)) == null)
			throw new NullPointerException();
	return tempNumStack.getMemberAt(BigInteger.valueOf(0));      // 임시 스택에 마지막 남은 숫자가 결과.
	}
}
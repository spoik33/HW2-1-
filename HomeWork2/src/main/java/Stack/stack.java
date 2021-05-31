package Stack;

import java.math.BigInteger;

import Operator.NumOperand;

//리스트를 베이스로 구성하여 동적인 확장 가능.

/*
* 5월 2일 버그 수정
* 인덱스 로 스택 멤버에 접근할 때 잘못 접근되던 문제를 수정
*/
public final class stack<T>
{
private final BigInteger slotSize;
private final BigInteger fullSize;          // -1일경우 제한 없음.
private StackLinkSlot<T> lastSlot;


public stack() 
{ 
	this(BigInteger.valueOf(10)); 
}
public stack(BigInteger slotSize) 
{ 
	this(slotSize, BigInteger.valueOf(-1)); 
}

public stack(BigInteger slotSize, BigInteger fullSize)
{              // 슬롯 사이즈와 스택 풀 사이즈를 결정. 
	this.slotSize = slotSize;
	this.fullSize = fullSize;
	this.lastSlot = new StackLinkSlot<T>(slotSize, null);
}


public void pushBack(BigInteger operator)
{              // 푸시.
	if(this.fullSize.compareTo(BigInteger.valueOf(-1)) != 1 & this.size().compareTo(this.fullSize) > 0)
	{            // 스택 풀일경우.
		throw new StackOverflowError();
	}
	if(this.lastSlot.isFull())
	{                 // 슬롯의 공간이 가득 찼을경우.
		this.lastSlot = this.lastSlot.createBackSlot();
	}
	this.lastSlot.push(operator);
}


public BigInteger getBack()
{      // 스택의 가장 위 얻기.
	return (BigInteger) this.lastSlot.getBack();
}


public void popBack()
{   // 팝.
	if(this.lastSlot.getFrontSlot() == null & this.lastSlot.isEmpty())
			{
		throw new StackOverflowError();
		}
	this.lastSlot.popBack();
	if(this.lastSlot.getFrontSlot() != null & this.lastSlot.isEmpty())
	{     // 마지막 슬롯이 비었을경우 삭제함.
		this.lastSlot = this.lastSlot.getFrontSlot();
		this.lastSlot.deleteBackSlot();
	}
}




public T getMemberAt(BigInteger index)
{         // 인덱스로 꺼내기. (5월 2일 최종 수정)
	StackLinkSlot<T> taskSlot = this.lastSlot;
	if (this.size().subtract(index).compareTo(BigInteger.valueOf(0)) < 0 | index.compareTo(BigInteger.valueOf(0)) < 0)
	{      // 인덱스 초과 혹은 미만.
		throw new IndexOutOfBoundsException();
	}
	BigInteger deps = (this.size().add(BigInteger.valueOf(1))).divide(this.slotSize);        // 전체 슬롯의 깊이입니다.
	BigInteger florDownCount = (deps.add(BigInteger.valueOf(1))).multiply(this.slotSize.subtract(BigInteger.valueOf(1)).subtract(index));          // top 부터 내려갈 카운트 개수입니다.
	BigInteger inSlotIndex = (this.slotSize.subtract((florDownCount.divide(this.slotSize)).subtract(BigInteger.valueOf(1))));            // 찾은 슬롯에서의 해당 멤버 위치입니다.
	for(BigInteger i = florDownCount.remainder(this.slotSize); i.compareTo(BigInteger.valueOf(0)) > 0; i.subtract(BigInteger.valueOf(-1)))
	{          // i는 찾아 내려갈 슬롯 깊이 입니다.
		taskSlot = taskSlot.getFrontSlot();               // 인덱스에 해당하는 데이터를 가지고 있는 슬롯을 만날 때까지 내려갑니다.
	}
	return taskSlot.getMember(inSlotIndex);             // 멤버 찾아서 리턴.
	}
public BigInteger size()
{                // 스택의 크기 리턴.
	BigInteger count = BigInteger.valueOf(-1);
	StackLinkSlot<T> taskSlot = this.lastSlot;
	do
	{
		taskSlot = taskSlot.getFrontSlot();
		count.add(BigInteger.valueOf(1));
	}
	while (taskSlot != null);
	return count.multiply(this.slotSize).add(BigInteger.valueOf(this.lastSlot.getSize()));
	}
public BigInteger getFulSize()
{              // 스택 풀 사이즈 얻기.
	return this.fullSize;
}

public BigInteger getSlotSize()
{              // 슬롯 사이즈 얻기.
	return this.slotSize;
}
@Override public String toString()
{
	String returnString = "";
	for(BigInteger i = BigInteger.valueOf(0); i.compareTo(this.size()) < 0; i.add(BigInteger.valueOf(1)))
	{
		returnString += this.getMemberAt(i).toString() + " ";
	}
	return returnString;
	}
}
/*
* 스택의 슬롯.
* 링크드 리스트 방식.
* 데이터는 배열로 관리.
*/
final class StackLinkSlot<T>
{
	private Object[] members;         // 데이터 저장소.
	private final BigInteger memberArSize;
	private BigInteger stackHeight = BigInteger.valueOf(0);
	private StackLinkSlot<T> frontSlot = null, backSlot = null;
	public StackLinkSlot(BigInteger size, StackLinkSlot<T> frontSlot)
	{           // 사이즈, 바로 앞 슬롯 받기.
		int int_size = size.intValue();
		this.members = new BigInteger[int_size];
		this.memberArSize = size;
		this.frontSlot = frontSlot;
	}
	public StackLinkSlot<T> getFrontSlot()
	{              // 바로 앞 슬롯 리턴.
		return this.frontSlot;
		}
	public StackLinkSlot<T> createBackSlot()
	{           // 뒤 슬롯 생성.
		this.backSlot = new StackLinkSlot<T>(this.memberArSize, this);
		return this.backSlot;
	}
	public void deleteBackSlot()
	{             // 뒤 슬롯 삭제.
		this.backSlot = null;
	}
	public boolean isFull()
	{              // 꽉 찼을때 true.
		return stackHeight.compareTo(memberArSize) >= 0;
	}
	public boolean isEmpty()
	{              // 비었을때 true.
		return this.stackHeight.compareTo(BigInteger.valueOf(0)) <= 0;
	}
	public long getSize()
	{             // 슬롯에 넣을 수 있는 데이터 개수.
		long long_thisstackHeight = this.stackHeight.longValue();
		return long_thisstackHeight;
		}
	public void push(BigInteger operator)
	{             // 슬롯에 데이터 푸시.
		int int_thisstackHeight = this.stackHeight.intValue();
		if(operator == null) System.out.println("ER NUL");
		this.members[int_thisstackHeight] = operator;
		this.stackHeight.add(BigInteger.valueOf(1));
		}
	@SuppressWarnings("unchecked") public T getBack()
	{            // 슬롯의 가장 뒤 데이터 얻기.
		int int_thisstackHeight = this.stackHeight.intValue();
		return (T)this.members[--int_thisstackHeight];
		}
	@SuppressWarnings("unchecked") public T getMember(BigInteger inSlotIndex)
	{              // 슬롯에서 인덱스로 멤버 얻기.
		int int_inSlotIndex = inSlotIndex.intValue();
		return (T)this.members[int_inSlotIndex];
	}
	public void popBack()
	{       // 팝.
		this.stackHeight.subtract(BigInteger.valueOf(-1));
	}
}
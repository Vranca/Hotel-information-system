package utility;

public class Tuple<F, S>
{
	private F firstValue = null;
	private S secondValue = null;

	public Tuple(F firstValue, S secondValue)
	{
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public final F getFirstValue()
	{
		return firstValue;
	}

	public final void setFirstValue(F firstValue)
	{
		this.firstValue = firstValue;
	}

	public final S getSecondValue()
	{
		return secondValue;
	}

	public final void setSecondValue(S secondValue)
	{
		this.secondValue = secondValue;
	}

}

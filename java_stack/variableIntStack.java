class variableIntStack {
	final private int MAXSIZE = 100;
	private int[][] stack = new int[MAXSIZE][];
	private int top = -1;

	public void push(int[] values) {
		stack[++top] = values;
	}

	public int[] pop() {
		return stack[top--];
	}
}
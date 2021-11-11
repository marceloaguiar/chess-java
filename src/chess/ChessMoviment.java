package chess;

public class ChessMoviment {
	
	private ChessPosition source;
	private ChessPosition target;
		
	public ChessMoviment(ChessPosition source, ChessPosition target) {
		this.source = source;
		this.target = target;
	}
	
	public ChessPosition getSource() {
		return source;
	}
	public void setSource(ChessPosition source) {
		this.source = source;
	}
	public ChessPosition getTarget() {
		return target;
	}
	public void setTarget(ChessPosition target) {
		this.target = target;
	}
	
    @Override
    public String toString(){
        return "" + source.getColumn() + source.getRow() + ":" + target.getColumn() + target.getRow();
    }	
	
	
}

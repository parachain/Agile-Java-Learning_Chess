package pieces;

public class Queen extends Piece {
    
    protected Queen(Piece.Color color) {
        this.color = color;
        
        if (color == Piece.Color.WHITE)
            this.representation = 'q';
        else
            this.representation = 'Q';
    }
    
    @Override
    public boolean is(Class type) {
        return type == Queen.class;
    }
}

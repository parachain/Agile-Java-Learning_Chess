package chess;

import junit.framework.TestCase;
import util.StringUtil;
import pieces.Piece;

public class BoardTest extends TestCase {
    private Board board;
    
    public void setUp() {
        board = Board.createInitializedBoard();
    }
    
    public void testCreate() {
        board = Board.createEmptyBoard();
        assertEquals(0, board.getNumberOfPieces());
        
        Piece.resetCount();
        board = Board.createInitializedBoard();
        assertEquals(32, board.getNumberOfPieces());
        assertEquals(16, Piece.getBlackPieceCount());
        assertEquals(16, Piece.getWhitePieceCount());
        
        String newline = StringUtil.NEWLINE;
        String expectedBoardPrint = "RNBQKBNR" + newline
                                      + "PPPPPPPP" + newline
                                      + "........" + newline
                                      + "........" + newline
                                      + "........" + newline
                                      + "........" + newline
                                      + "pppppppp" + newline
                                      + "rnbqkbnr" + newline;
        assertEquals(expectedBoardPrint, board.getPrint());
    }
    
    public void testGetNumberOfPieces() {        
        int whitePawnNumber = 
            board.getNumberOfPieces(Piece.Color.WHITE, Piece.Type.PAWN);
        assertEquals(8, whitePawnNumber);
        
        int blackBishopNumber = 
            board.getNumberOfPieces(Piece.Color.BLACK, Piece.Type.BISHOP);
        assertEquals(2, blackBishopNumber);
        
        // More tests ...
        
    }
    
    public void testRetrievePiece() {        
        Piece piece = board.getPieceAt('a', 8);
        verifyPiece(piece, Piece.Color.BLACK, Piece.Type.ROOK);
        
        piece = board.getPieceAt('e', 1);
        verifyPiece(piece, Piece.Color.WHITE, Piece.Type.KING);
    }
    
    public void testPlacePiece() {
        board = Board.createEmptyBoard();
        
        board.placePiece('b', 6, Piece.createBlackKing());
        board.placePiece('b', 5, Piece.createBlackRook());
        board.placePiece('c', 4, Piece.createWhiteKing());
        
        Piece piece = board.getPieceAt('b', 6);
        verifyPiece(piece, Piece.Color.BLACK, Piece.Type.KING);
        
        piece = board.getPieceAt('b', 5);
        verifyPiece(piece, Piece.Color.BLACK, Piece.Type.ROOK);
        
        piece = board.getPieceAt('c', 4);
        verifyPiece(piece, Piece.Color.WHITE, Piece.Type.KING);
    }
    
    private void verifyPiece(Piece piece, Piece.Color color, Piece.Type type) {
        assertEquals(color, piece.getColor());
        assertEquals(type, piece.getType());
    }
    
    public void testStrength() {
        board = board.createEmptyBoard();
        
        // Black side
        board.placePiece('b', 6, Piece.createBlackPawn());
        assertEquals(1.0, board.getBlackSideStrength());        
        board.placePiece('e', 6, Piece.createBlackQueen());
        assertEquals(10.0, board.getBlackSideStrength());
        
        board.placePiece('a', 7, Piece.createBlackPawn());
        board.placePiece('c', 7, Piece.createBlackPawn());
        board.placePiece('d', 7, Piece.createBlackBishop());
        board.placePiece('b', 8, Piece.createBlackKing());
        board.placePiece('c', 8, Piece.createBlackRook());
        assertEquals(20.0, board.getBlackSideStrength());
        
        // White side
        board.placePiece('f', 4, Piece.createWhiteKnight());
        board.placePiece('g', 4, Piece.createWhiteQueen());
        board.placePiece('f', 3, Piece.createWhitePawn());
        board.placePiece('h', 3, Piece.createWhitePawn());
        assertEquals(13.5, board.getWhiteSideStrength());
        
        board.placePiece('f', 2, Piece.createWhitePawn());
        board.placePiece('g', 2, Piece.createWhitePawn());
        board.placePiece('e', 1, Piece.createWhiteRook());
        board.placePiece('f', 1, Piece.createWhiteKing());
        assertEquals(19.5, board.getWhiteSideStrength());
        
        // Verify that strength of black side keep unchanged
        assertEquals(20.0, board.getBlackSideStrength());
    }
}

public class Pawn extends Piece {

    public Pawn(int x, int y, Side side, Board board) {
        // TODO: Call super constructor
        super(x,y,side,board);
    }

    @Override
    public boolean canMove(int destX, int destY) {

        //TODO: Check piecerules.md for the movement rule for this piece :)
        if (this.getSide() == Side.BLACK){
            if (destY == this.y+1 && board.get(this.x,this.y+1)==null){
                if (destX == this.x){
                    return true;
                }
            }
            if (this.y == 1 && board.get(this.x,this.y+1)==null){
                if(destY == this.y+2){
                    if (destX == this.x){
                        return true;
                    }
                }
            }
            if (this.x >=1 && this.x <=6 && this.y>=0 && this.y<=6) {
                if (destX == this.x + 1) {
                    if (board.get(this.x + 1, this.y + 1) != null) {
                        if (destY == this.y + 1) {
                            return true;
                        }
                    }
                }
                if (destX == this.x - 1){
                    if (board.get(this.x - 1, this.y + 1) != null) {
                        if (destY == this.y + 1) {
                            return true;
                        }
                    }
                }


            }
            if (this.x == 0){
                if (board.get(this.x+1, this.y+1)!= null ){
                    if (destX == this.x + 1){
                        if (destY == this.y+1){
                            return true;
                        }
                    }
                }
            }
            if (this.x == 7){
                if (board.get(this.x-1, this.y+1)!= null ){
                    if (destX == this.x - 1){
                        if (destY == this.y+1){
                            return true;
                        }
                    }
                }
            }

        }
        if (this.getSide() == Side.WHITE){
            if (destY == this.y-1 && board.get(this.x,this.y-1)==null){
                if (destX == this.x){
                    return true;
                }
            }
            if (this.y == 6 && board.get(this.x,this.y-2)==null) {
                if (destY == this.y - 2) {
                    if (destX == this.x) {
                        return true;
                    }
                }
            }
            if (this.x >=1 && this.x <=6 && this.y>=0 && this.y<=6) {
                if (destX == this.x + 1) {
                    if (board.get(this.x + 1, this.y - 1) != null) {
                        if (destY == this.y - 1) {
                            return true;
                        }
                    }
                }
                if (destX == this.x - 1){
                    if (board.get(this.x - 1, this.y - 1) != null) {
                        if (destY == this.y - 1) {
                            return true;
                        }
                    }
                }
            }
            if (this.x == 0){
                if (board.get(this.x+1, this.y-1)!= null ){
                    if (destX == this.x + 1){
                        if (destY == this.y-1){
                            return true;
                        }
                    }
                }
            }
            if (this.x == 7){
                if (board.get(this.x-1, this.y-1)!= null ){
                    if (destX == this.x - 1){
                        if (destY == this.y-1){
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }



    @Override
    public String getSymbol() {
        return this.getSide() == Side.BLACK ? "♟" : "♙";
    }
}

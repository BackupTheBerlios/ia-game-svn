package diabalik;

import java.util.StringTokenizer;

public class PositionGrid2D {
	
	/* Some pre-computed relative positions */
	public static PositionGrid2D UP = new PositionGrid2D(0,1);
	public static PositionGrid2D UPRIGHT = new PositionGrid2D(1,1);
	public static PositionGrid2D RIGHT = new PositionGrid2D(1,0);
	public static PositionGrid2D DOWNRIGHT = new PositionGrid2D(1,-1);
	public static PositionGrid2D DOWN = new PositionGrid2D(0,-1);
	public static PositionGrid2D DOWNLEFT = new PositionGrid2D(-1,-1);
	public static PositionGrid2D LEFT = new PositionGrid2D(-1,0);
	public static PositionGrid2D UPLEFT = new PositionGrid2D(-1,1);
	public static PositionGrid2D HERE = new PositionGrid2D(0,0);
	public static PositionGrid2D basicDir[] = { UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN,
												DOWNLEFT, LEFT, UPLEFT, HERE };	
	
	public int x;
	public int y;
	
	public PositionGrid2D( int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public PositionGrid2D( String representation)
	{
		extract(representation);
	}
	
	
	public void setPosition( PositionGrid2D other )
	{
		this.x = other.x;
		this.y = other.y;
	}
	public PositionGrid2D add( PositionGrid2D other)
	{
		return new PositionGrid2D( x+other.x, y+other.y);
	}
	
	/**
	 * Are this PositionGrid2D aligned in one of 8 dimensions.
	 * @param other
	 * @return
	 */
	public boolean isAligned( PositionGrid2D other )
	{
		if( this.x == other.x ) return true;
		if( this.y == other.y ) return true;
		if( Math.abs(other.x - this.x) == Math.abs(other.y - this.y)) return true;
		return false;
	}
	public PositionGrid2D getDirectionTo( PositionGrid2D other )
	{
		if( isAligned(other) == false ) return HERE;
		if( this.x == other.x ) {
			if( this.y > other.y ) return DOWN;
			if( this.y == other.y ) return HERE;
			if( this.y < other.y ) return UP;
		}
		else if( this.x > other.x ) {
			if( this.y > other.y ) return DOWNLEFT;
			if( this.y == other.y ) return LEFT;
			if( this.y < other.y ) return UPLEFT;
		}
		else {
			if( this.y > other.y ) return DOWNRIGHT;
			if( this.y == other.y ) return RIGHT;
			if( this.y < other.y ) return UPRIGHT;
		}
		return HERE;
	}
	
	
	public String toString()
	{
		StringBuffer tmpStr = new StringBuffer();
		tmpStr.append( "( "+x+", "+y+")");
		
		return tmpStr.toString();
	}
	
	void extract( String buff )
	{
		StringTokenizer st = new StringTokenizer( buff, ":(,+ \t\n\r\f)");
		String token;
		
		token = st.nextToken();
		this.x = Integer.parseInt(token);
		token = st.nextToken();
		this.y = Integer.parseInt(token);
	}
}

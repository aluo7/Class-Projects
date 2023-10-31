public class Show implements IShow{
	// create objects for show title, show year, show rating, and show's providers
	private String title;
	private int year;
	private int rating;
	private String providers;

	//construct a Show given parameters
	public Show(String title, int year, int rating, String providers)
	{
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.providers =  providers;
	}

	// default constructor
	public Show(){
	title = null;
	year = 0;
	rating = 0;
	providers = null;
	}

	//return title of show
	public String getTitle() {
		return this.title;
	}
	//return year
	public int getYear(){
		return this.year;
	}
	//return rating show received
	public int getRating(){
		return this.rating;
	}
	//return streaming services where show can be watched
	public String getProviders() {
		return this.providers;
	
	}
	public boolean isAvailableOn(String provider)
	{
		if(this.providers.contains(provider))
			{
				return true;
			}
		return false;
	}

	@Override
	public int compareTo(Show s)
	{
		if(this.getRating()==s.getRating())
			{return 0;}
		if(this.getRating()>s.getRating())
                        {return 1;}
		return -1;
		
	}
}

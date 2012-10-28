package ac.cr.ecci.ucr.droidmusic.bo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// 
@DatabaseTable(tableName =  "Song")
public class Song {
	
	@DatabaseField
	String wrapperType;
	@DatabaseField
	String kind;
	@DatabaseField
	int artistId;
	@DatabaseField
	int collectionId;
	@DatabaseField (id = true)
	int trackId;
	@DatabaseField
	String artistName;
	@DatabaseField
	String collectionName;
	@DatabaseField
	String trackName;
	@DatabaseField
	String collectionCensoredName;
	@DatabaseField
	String trackCensoredName;
	@DatabaseField
	String artistViewUrl;
	@DatabaseField
	String collectionViewUrl;
	@DatabaseField
	String trackViewUrl;
	@DatabaseField
	String previewUrl;
	@DatabaseField
	String artworkUrl30;
	@DatabaseField
	String artworkUrl60;
	@DatabaseField
	String artworkUrl100;
	@DatabaseField
	double collectionPrice;
	@DatabaseField
	double trackPrice;
	@DatabaseField
	String releaseDate;
	@DatabaseField
	String collectionExplicitness;
	@DatabaseField
	String trackExplicitness;
	@DatabaseField
	int discCount;
	@DatabaseField
	int discNumber;
	@DatabaseField
	int trackCount;
	@DatabaseField
	int trackNumber;
	@DatabaseField
	int trackTimeMillis;
	@DatabaseField
	String country;
	@DatabaseField
	String currency;
	@DatabaseField
	String primaryGenreName;
	
	public Song(){
		
	}
	
	public Song(String songName, String artist, String thumbnail) {
		this.trackName = songName;
		this.artistName = artist;
		this.artworkUrl30 = thumbnail;
	}

	public Song(String wrapperType, String kind, int artistId,
			int collectionId, int trackId, String artistName,
			String collectionName, String trackName,
			String collectionCensoredName, String trackCensoredName,
			String artistViewUrl, String collectionViewUrl,
			String trackViewUrl, String previewUrl, String artworkUrl30,
			String artworkUrl60, String artworkUrl100, double collectionPrice,
			double trackPrice, String releaseDate,
			String collectionExplicitness, String trackExplicitness,
			int discCount, int discNumber, int trackCount, int trackNumber,
			int trackTimeMillis, String country, String currency,
			String primaryGenreName) {
		this.wrapperType = wrapperType;
		this.kind = kind;
		this.artistId = artistId;
		this.collectionId = collectionId;
		this.trackId = trackId;
		this.artistName = artistName;
		this.collectionName = collectionName;
		this.trackName = trackName;
		this.collectionCensoredName = collectionCensoredName;
		this.trackCensoredName = trackCensoredName;
		this.artistViewUrl = artistViewUrl;
		this.collectionViewUrl = collectionViewUrl;
		this.trackViewUrl = trackViewUrl;
		this.previewUrl = previewUrl;
		this.artworkUrl30 = artworkUrl30;
		this.artworkUrl60 = artworkUrl60;
		this.artworkUrl100 = artworkUrl100;
		this.collectionPrice = collectionPrice;
		this.trackPrice = trackPrice;
		this.releaseDate = releaseDate;
		this.collectionExplicitness = collectionExplicitness;
		this.trackExplicitness = trackExplicitness;
		this.discCount = discCount;
		this.discNumber = discNumber;
		this.trackCount = trackCount;
		this.trackNumber = trackNumber;
		this.trackTimeMillis = trackTimeMillis;
		this.country = country;
		this.currency = currency;
		this.primaryGenreName = primaryGenreName;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getArtworkUrl60() {
		return artworkUrl60;
	}

	public void setArtworkUrl60(String artworkUrl60) {
		this.artworkUrl60 = artworkUrl60;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("wrapperType=").append(wrapperType).append(",");
		sb.append("kind=").append(kind).append(",");
		sb.append("artistId=").append(artistId).append(",");
		sb.append("collectionId=").append(collectionId).append(",");
		sb.append("trackId=").append(trackId).append(",");
		sb.append("artistName=").append(artistName).append(",");
		sb.append("collectionName=").append(collectionName).append(",");
		sb.append("trackName=").append(trackName).append(",");
		sb.append("collectionCensoredName=").append(collectionCensoredName).append(",");
		sb.append("trackCensoredName=").append(trackCensoredName).append(",");
		sb.append("artistViewUrl=").append(artistViewUrl).append(",");
		sb.append("collectionViewUrl=").append(collectionViewUrl).append(",");
		sb.append("trackViewUrl=").append(trackViewUrl).append(",");
		sb.append("previewUrl=").append(previewUrl).append(",");
		sb.append("artworkUrl30=").append(artworkUrl30).append(",");
		sb.append("artworkUrl60=").append(artworkUrl60).append(",");
		sb.append("artworkUrl100=").append(artworkUrl100).append(",");
		sb.append("collectionPrice=").append(collectionPrice).append(",");
		sb.append("trackPrice=").append(trackPrice).append(",");
		sb.append("releaseDate=").append(releaseDate).append(",");
		sb.append("collectionExplicitness=").append(collectionExplicitness).append(",");
		sb.append("trackExplicitness=").append(trackExplicitness).append(",");
		sb.append("discCount=").append(discCount).append(",");
		sb.append("discNumber=").append(discNumber).append(",");
		sb.append("trackCount=").append(trackCount).append(",");
		sb.append("trackNumber=").append(trackNumber).append(",");
		sb.append("trackTimeMillis=").append(trackTimeMillis).append(",");
		sb.append("country=").append(country).append(",");
		sb.append("currency=").append(currency).append(",");
		sb.append("primaryGenreName=").append(primaryGenreName);
		
		return sb.toString();
	}

	
	
}

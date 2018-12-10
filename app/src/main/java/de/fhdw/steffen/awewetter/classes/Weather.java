package de.fhdw.steffen.awewetter.classes;

public class Weather
{
    public double getCurrentTmp() {
        return currentTmp;
    }

    public void setCurrentTmp(double currentTmp) {
        this.currentTmp = currentTmp;
    }

    private double currentTmp = 0;
    private String dayWeather = "";
    private String iconWeather = "";
    private double tempMaxWeather = 0;
    private double tempMinWeather = 0;
    private double windSpeedWeather = 0;
    private  String windDirectionWeather = "";
    private String city;
    private String sunrise = "";
    private double humidity = 0;
    private String sunset = "";


    //Immoment nur zum Testen:
    public Weather()
    {

    }

    public Weather(String city, String day, String icon, double tempMax, double tempMin, double windSpeed, String windDirection)
    {
        this.city = city;
        this.dayWeather = day;
        this. iconWeather = icon;
        this.tempMaxWeather = tempMax;
        this.tempMinWeather = tempMin;
        this.windSpeedWeather = windSpeed;
        this.windDirectionWeather = windDirection;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getIconWeather() {
        return iconWeather;
    }

    public void setIconWeather(String iconWeather) {
        this.iconWeather = iconWeather;
    }

    public double getTempMaxWeather() {
        return tempMaxWeather;
    }

    public void setTempMaxWeather(double tempMaxWeather) {
        this.tempMaxWeather = tempMaxWeather;
    }

    public double getTempMinWeather() {
        return tempMinWeather;
    }

    public void setTempMinWeather(double tempMinWeather) {
        this.tempMinWeather = tempMinWeather;
    }

    public double getWindSpeedWeather() {
        return windSpeedWeather;
    }

    public void setWindSpeedWeather(double windSpeedWeather) {
        this.windSpeedWeather = windSpeedWeather;
    }

    public String getWindDirectionWeather() {
        return windDirectionWeather;
    }

    public void setWindDirectionWeather(String windDirectionWeather) {
        this.windDirectionWeather = windDirectionWeather;
    }
}

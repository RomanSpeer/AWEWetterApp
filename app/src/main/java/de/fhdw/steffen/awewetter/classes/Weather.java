package de.fhdw.steffen.awewetter.classes;

public class Weather
{
    private String dayWeather = "";
    private String iconWeather = "";
    private double tempMaxWeather = 0;
    private double tempMinWeather = 0;
    private double windSpeedWeather = 0;
    private  String windDirectionWeather = "";


    //Immoment nur zum Testen:
    public Weather()
    {

    }

    public Weather(String day, String icon, double tempMax, double tempMin, double windSpeed, String windDirection)
    {
        this.dayWeather = day;
        this. iconWeather = icon;
        this.tempMaxWeather = tempMax;
        this.tempMinWeather = tempMin;
        this.windSpeedWeather = windSpeed;
        this.windDirectionWeather = windDirection;
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

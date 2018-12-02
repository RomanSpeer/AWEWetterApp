package de.fhdw.steffen.awewetter.classes;

public class Weather
{
    private String dayWeather = "";
    private String iconWeather = "";
    private String tempMaxWeather = "";
    private String tempMinWeather = "";
    private String windSpeedWeather = "";
    private  String windDirectionWeather = "";


    //Immoment nur zum Testen:
    public Weather()
    {

    }

    public Weather(String day, String icon, String tempMax, String tempMin, String windSpeed, String windDirection)
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

    public String getTempMaxWeather() {
        return tempMaxWeather;
    }

    public void setTempMaxWeather(String tempMaxWeather) {
        this.tempMaxWeather = tempMaxWeather;
    }

    public String getTempMinWeather() {
        return tempMinWeather;
    }

    public void setTempMinWeather(String tempMinWeather) {
        this.tempMinWeather = tempMinWeather;
    }

    public String getWindSpeedWeather() {
        return windSpeedWeather;
    }

    public void setWindSpeedWeather(String windSpeedWeather) {
        this.windSpeedWeather = windSpeedWeather;
    }

    public String getWindDirectionWeather() {
        return windDirectionWeather;
    }

    public void setWindDirectionWeather(String windDirectionWeather) {
        this.windDirectionWeather = windDirectionWeather;
    }
}

/**
 * Weather-Klasse
 * Wetter-Objekt, welche alle Informationen zu einer Wetterinformation enthält.
 * Informationen können über den Konstruktor oder über Setter-Methoden gesetzt werden und
 * über Getter können die einzelnen Werte zurückgegeben werden.
 *
 * @author Roman Speer, Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.classes;

public class Weather {
    private double currentTmp = 0;
    private String dayWeather = "";
    private String iconWeather = "";
    private double tempMaxWeather = 0;
    private double tempMinWeather = 0;
    private double windSpeedWeather = 0;
    private String windDirectionWeather = "";
    private String city;
    private String sunrise = "";
    private double humidity = 0;
    private String sunset = "";


    /**
     * Leerer Konstruktor
     */
    public Weather() {

    }

    /**
     * Konstruktor, in dem die Wetterdaten übergeben werden.
     *
     * @param city          Ort, für den die Wetterdaten bestimmt sind
     * @param day           Tag, für den die Wetterdaten bestimmt sind
     * @param icon          Icon für die entsprechende Wetterbeschreibung
     * @param tempMax       Maximale Temperatur die erreicht werden kann
     * @param tempMin       Minimale Temperatur die erreicht werden kann
     * @param windSpeed     Windgeschwindigkeit der Wetterlage
     * @param windDirection Windrichtung der Wetterlage
     */
    public Weather(String city, String day, String icon, double tempMax, double tempMin, double windSpeed, String windDirection) {
        this.city = city;
        this.dayWeather = day;
        this.iconWeather = icon;
        this.tempMaxWeather = tempMax;
        this.tempMinWeather = tempMin;
        this.windSpeedWeather = windSpeed;
        this.windDirectionWeather = windDirection;
    }

    /**
     * Zurückgeben der aktuellen Temperatur
     *
     * @return Aktuelles Temperatur als double
     */
    public double getCurrentTmp() {
        return currentTmp;
    }

    /**
     * Setzen der Aktuellen Temperatur
     *
     * @param currentTmp Aktuelle Wettertemperatur
     */
    public void setCurrentTmp(double currentTmp) {
        this.currentTmp = currentTmp;
    }

    /**
     * Zurückgeben der Luftfeuchtigeit
     *
     * @return Luftfeuchtigkeit als double
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Setzen der Luftfeuchtigkeit
     *
     * @param humidity Wert der Luftfeuchtigkeit
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Zurückgeben des Sonnenuntergangs-Zeit
     *
     * @return Zeit des Sonnenuntergangs als String
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * Setzen der Sonnenuntergangs-Zeit
     *
     * @param sunset Zeit für den Sonnenuntergang
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    /**
     * Zurückgeben der Sonnenaufgang-Zeit
     *
     * @return Zeit des Sonnenaufgangs als String
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * Setzen des Sonnenaufgangszeit
     * @param sunrise Zeit des Sonnenaufgangs
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * Zurückgeben der Stadt
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Setzen der Stadt
     *
     * @param city die Stadt
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Zurückgeben des Datums
     *
     * @return Datum
     */
    public String getDayWeather() {
        return dayWeather;
    }

    /**
     * Setzen des Datums
     *
     * @param dayWeather Datums
     */
    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    /**
     * Zurückgeben des Icons
     *
     * @return Icon
     */
    public String getIconWeather() {
        return iconWeather;
    }

    /**
     * Setzen des Icons
     *
     * @param iconWeather Icon
     */
    public void setIconWeather(String iconWeather) {
        this.iconWeather = iconWeather;
    }

    /**
     * Zurückgeben der maximalen Temperatur
     *
     * @return maximale Temperatur
     */
    public double getTempMaxWeather() {
        return tempMaxWeather;
    }

    /**
     * Setzen der maximalen Temperatur
     *
     * @param tempMaxWeather maximale Temperatur
     */
    public void setTempMaxWeather(double tempMaxWeather) {
        this.tempMaxWeather = tempMaxWeather;
    }

    /**
     * zurückgeben der minimalen Temperatur
     *
     * @return minimale Temperatur
     */
    public double getTempMinWeather() {
        return tempMinWeather;
    }

    /**
     * setzen der minimalen Temperatur
     *
     * @param tempMinWeather minimale Temperatur
     */
    public void setTempMinWeather(double tempMinWeather) {
        this.tempMinWeather = tempMinWeather;
    }

    /**
     * zurückgeben der Windgeschwindigkeit
     *
     * @return Windgeschwindigkeit
     */
    public double getWindSpeedWeather() {
        return windSpeedWeather;
    }

    /**
     * Setzen der Windgeschwindigkeit
     *
     * @param windSpeedWeather Windgeschwindigkeit
     */
    public void setWindSpeedWeather(double windSpeedWeather) {
        this.windSpeedWeather = windSpeedWeather;
    }

    /**
     * Zurückgeben der Windrichtung
     *
     * @return Windrichtung
     */
    public String getWindDirectionWeather() {
        return windDirectionWeather;
    }

    /**
     * Setzen der Windrichtung
     *
     * @param windDirectionWeather Windrichtung
     */
    public void setWindDirectionWeather(String windDirectionWeather) {
        this.windDirectionWeather = windDirectionWeather;
    }
}

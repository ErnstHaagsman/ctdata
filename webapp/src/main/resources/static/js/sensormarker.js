/**
 * Created by eh on 12/1/2015.
 */

var SensorMarker = (function(){

    var icon = {
        url: 'img/marker.png',
        size: new google.maps.Size(62, 32),
        origin: new google.maps.Point(0, 0),
        anchor: new google.maps.Point(31, 32)
    };

    /**
     *
     * @param {google.maps.Map} map The map to draw on
     * @param opts.latitude Latitude of the sensor
     * @param opts.longitude Longitude of the sensor
     * @param opts.observation Value of the sensor's last observation
     * @param opts.uuid Raspberry node UUID
     * @param opts.number Number of the sensor
     * @param opts.name Name of the sensor
     */
    function Sensor_marker(map, opts){
        this.marker = new MarkerWithLabel({
            icon: icon,
            map: map,
            draggable: false,
            position: new google.maps.LatLng(opts.latitude, opts.longitude),
            labelClass: 'sensorlabels',
            labelContent: opts.observation,
            labelAnchor: new google.maps.Point(31, 27)
        });

        this.uuid = opts.uuid;
        this.number = opts.number;
        this.name = opts.name;
        this.observation = opts.observation;

        var self = this;
        google.maps.event.addListener(this.marker, 'click', function(e){
            if (self.hasOwnProperty('onClick') && typeof(self.onClick) === 'function'){
                self.onClick(self);
            }
        });
    }

    Sensor_marker.prototype.new_observation = function(observation){
        this.observation = observation;
        this.marker.set('labelContent', observation);
        this.marker.set('labelStyle', {color: "lime"});
        var self = this;
        setTimeout(function(){
            self.marker.set('labelStyle', {color: "white"});
        }, 2000);
    };

    return Sensor_marker;

})();


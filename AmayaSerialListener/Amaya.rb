require 'uri'
require 'net/http'
require 'net/https'
require 'serialport'
require 'rubyserial'
require 'date'
if __FILE__ == $0
	post_req = lambda { uri = URI.parse("https://android.googleapis.com/gcm/send")
	    https = Net::HTTP.new(uri.host,uri.port)
	    https.use_ssl = true
	    req = Net::HTTP::Post.new(uri.path)
	    req.add_field('Authorization', 'key=AIzaSyBVZc7qt_OMU_MBvxj8-bGpNUb1PlF-eL8')
	    req.add_field("Content-Type", "application/json")
	    req.body = '{
		"to": "/topics/global",
			"data": {
				"title": "Amaya",
				"message": "¡Alerta, se ha detectado movimiento!"
	 		}	
		}'
	    res = https.request(req)
		puts res.body
	}
	post_not = lambda {
		uri = URI.parse("http://localhost/notifications")
	    https = Net::HTTP.new(uri.host,uri.port)
	    req = Net::HTTP::Post.new(uri.path)
	    req.add_field("Content-Type", "application/json")
	    req.add_field('Accept', 'application/json')
	    req.body = "{
			\"notifications\": {
				\"arduino_id\": \"1\",
				\"time\": \"#{DateTime.now}\"
	 		}	
		}"
	    res = https.request(req)
		puts res.body

	}
	begin
    	serial_port = Serial.new ARGV[0], 9600
    	while true
    		unless ((i = serial_port.read(10)) == "")
    			puts i
    			post_req.call
    			post_not.call
    		end
    	end  	
	rescue
		puts "Error: #{$!}"
	end
end
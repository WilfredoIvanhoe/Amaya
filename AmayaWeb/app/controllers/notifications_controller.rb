class NotificationsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	def show
		if request.headers["Accept"] == 'application/json'
			@notifications = Notification.all
			render json: @notifications, status: 200
		else
			if is_logged?
				@notifications = Notification.all
			end
		end
	end
	def create
		puts "numa"
		puts "JIDERS: \n\n\n#{request.headers["Content-Type"]}\n\n\n"
		@notification = Notification.new(notif_params)
		if @notification.save
			render nothing: true, status: 200
		else
			render nothing: true, status: 500
		end
	end

	private
	def is_logged?
		if session[:user]
			return true
		else
			return false
		end
	end
	def notif_params
		params.require(:notifications).permit(:arduino_id,:time)
	end
end

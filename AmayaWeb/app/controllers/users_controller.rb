class UsersController < ApplicationController
	def new
		unless is_logged?
			@user = User.new 
		else
			redirect_to '/'
		end
	end
	def show
		if is_user? params[:id].to_i
			@user = User.find(params[:id]) 
		else
			redirect_to '/'
		end
		rescue ActiveRecord::RecordNotFound
			render template: '/404.html'
	end
	def create 
		@user = User.new(user_params)

		if @user.save and !is_logged?
			session[:user] = @user.id
			redirect_to @user
		else
			render 'new'
		end
	end
	private
	def user_params
		params.require(:users).permit(:mail, :password, :arduino_id)
	end

	def is_logged?
		if session[:user]
			return true
		else
			return false
		end
	end
	def is_user?(id)
		if session[:user] and session[:user].to_i == id
			return true
		else
			return false
		end
	end
end

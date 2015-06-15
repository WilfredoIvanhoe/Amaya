class SessionsController < ApplicationController
	skip_before_filter :verify_authenticity_token, :if => Proc.new { |c| c.request.format == 'application/json' }
	def login
		if on_session?
			redirect_to '/'
		end
	end

	def loging
		if @user = User.login(login_params)  and !on_session?
			if request.headers["Accept"] == 'application/json'
				render json: @user, status: 200
			else
				session[:user] = @user.id
				redirect_to @user
			end
		else
			if request.headers["Accept"] == 'application/json'
				render nothing: true, status: 400
			else
				flash[:notice] = 'Nombre de usuario o contraseña incorrecta'
				redirect_to '/login'
			end
		end
	end

	def logout
		if session[:user]
			session[:user] = nil
			render 'logout'
		else
			redirect_to root_path
		end
	end
	private
	def on_session?
		if session[:user]
			return true
		else
			return false
		end
	end
	def login_params
		params.require(:login).permit(:email, :password)
	end	

end

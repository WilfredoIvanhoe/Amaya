class User < ActiveRecord::Base
	Rails.application.config.filter_parameters << :password

	validates :mail, uniqueness: true

	def self.login(parameters)

		email = parameters[:email]
		password = parameters[:password]	
		if (user = User.find_by mail: email) 
			if user.password == password
				return user
			else
				return nil
			end
		else
			return nil
		end
	end	

end

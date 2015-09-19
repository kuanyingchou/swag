module Api
	module V1
		class SessionsController < ApplicationController
			def create
				email = params[:email]
				password = params[:password]
				device_token = params[:device_token] # for push notifications to devices
				device_type = params[:device_type] # to know what notification to send
				user = User.find_by(email: email)
				if user && user.authenticate(password)
					#unless user.device_tokens.exists?(device_token: device_token)
					#	user.device_tokens.new(device_token: device_token, device_type: device_type).save # hopefully this line works
					#end
					render json: user.auth_token, status: 200
				else
					self.headers['WWW-Authenticate'] = 'Token realm="Application"'
					render json: 'Bad credentials', status: 401
				end
			end
			def destroy
				token = params[:token]
				user = User.find_by(auth_token: token)
				if user
					user.reset_auth_token
					render json: 'Logged out', status: 204
				else
					render json: 'Invalid token', status: 401
				end
			end
		end
	end
end
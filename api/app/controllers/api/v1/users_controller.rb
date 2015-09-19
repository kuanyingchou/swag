module Api
	module V1
		class UsersController < ApplicationController
			def create
				@user = User.create(name: params[:name], email: params[:email], password: params[:password])
				if @user.save
					render json: @user, status: 204
				else
					render json: {"error": "failed to create resource"}, status: 422
				end
			end
			def show
				@user = User.find(params[:id])
				render json: @user, status: 200
			end
		end
	end
end
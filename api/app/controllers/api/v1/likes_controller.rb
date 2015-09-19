module Api
	module V1
		class LikesController < ApplicationController
			def create
				@like = Like.create(user_id: params[:user_id], drawing_id: params[:drawing_id])
				render json: {"message": "like created"}, status: 204
			end
			def destroy
				@like = Like.destroy
				render json: {"message": "like destroyed"}, status: 204
			end
		end
	end
end
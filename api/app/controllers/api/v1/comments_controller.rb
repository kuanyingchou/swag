module Api
	module V1
		class CommentsController < ApplicationController
			def create
				@comment = Comment.create(user_id: params[:user_id], drawing_id: params[:drawing_id], text: params[:text])
				if @comment.save
					render json: @comment, status: 204
				else
					render json: {"error": "failed to create resource"}
				end
			end
		end
	end
end
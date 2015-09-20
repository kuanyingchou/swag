module Api
	module V1
		class DrawingsController < ApplicationController
			def index
				@drawings = Drawing.all
				render json: @drawings, status: 200
			end
			def create
				@drawing = Drawing.create(user_id: params[:user_id], svg: params[:svg]) # upload file
				# randomly pick a chinese character
				if @drawing.save
					render json: @drawing, status: 204
				else
					render json: {"error": "failed to create resource"}, status: 422
				end
			end
			def show
				@drawing = Drawing.find(params[:id])
				render json: { drawing: @drawing, comments: @drawing.comments, likes: @drawing.likes.count }, status: 200
			end
		end
	end
end
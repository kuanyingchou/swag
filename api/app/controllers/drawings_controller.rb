class DrawingsController < ApplicationController
	def index
		@drawings = Drawing.all
	end
	def show
		@drawing = Drawing.find(params[:id])
	end
end

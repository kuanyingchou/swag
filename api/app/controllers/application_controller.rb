class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception
  skip_before_action :verify_authenticity_token, if: :json_request?
  helper_method :current_user, :logged_in
  protected
  # api requests
	def authenticate
    authenticate_token || render_unauthorized
  end

  def authenticate_token
    authenticate_with_http_token do |token, options|
      User.find_by(auth_token: token)
    end
  end

  def render_unauthorized
    self.headers['WWW-Authenticate'] = 'Token realm="Application"'
    render json: 'Bad credentials', status: 401
  end

  private

  def json_request?
    request.format.json?
  end
  
  # sessions
  def current_user
    @current_user ||= User.find_by(id: session[:user_id])
  end

  def logged_in
    unless current_user
      redirect_to root_url
    end
  end
end

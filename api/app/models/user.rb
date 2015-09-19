class User < ActiveRecord::Base
	has_many :drawings
	has_many :likes
	has_many :comments

	has_secure_password
end

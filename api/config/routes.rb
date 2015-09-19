Rails.application.routes.draw do
  namespace :api do
    namespace :v1 do

      resources :users

      resources :drawings do
        resources :likes
        resources :comments
      end

    end
  end
end

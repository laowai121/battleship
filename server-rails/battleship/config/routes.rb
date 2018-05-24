Rails.application.routes.draw do
  root 'battleship#index'
  post 'game/create', to: 'game#create'
  post 'game/join', to: 'game#join'
  post 'game/submit_ships', to: 'game#submit_ships'
  get 'game/get_state', to: 'game#get_state'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end

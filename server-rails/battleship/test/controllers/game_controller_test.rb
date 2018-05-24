require 'test_helper'

class GameControllerTest < ActionDispatch::IntegrationTest
  test "should get create" do
    get game_create_url
    assert_response :success
  end

  test "should get join" do
    get game_join_url
    assert_response :success
  end

  test "should get submit_ships" do
    get game_submit_ships_url
    assert_response :success
  end

end

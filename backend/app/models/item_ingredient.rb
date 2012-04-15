class ItemIngredient < ActiveRecord::Base
  belongs_to :item
  belongs_to :ingredient
end

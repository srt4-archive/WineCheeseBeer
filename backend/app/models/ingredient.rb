class Ingredient < ActiveRecord::Base
  #attr_accessible :name, :item, :id
  belongs_to :item
end
